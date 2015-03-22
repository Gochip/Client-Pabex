package com.client;

import com.client.listener.MessageListener;
import com.client.listener.InformationListener;
import com.client.listener.DisconnectListener;
import com.client.listener.ImageListener;
import com.client.listener.ObjectListener;
import com.difusion.ImageDifusion;
import com.difusion.ObjectDiffusion;
import executors.response.CommandResponse;
import executors.response.DisconnectResponse;
import executors.response.MessageResponse;
import executors.response.InformationResponse;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Es el hilo escuchador que de acuerdo al Response que llega del servidor
 * actúa.
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
class InputThread implements Runnable {

    /**
     * Flujo de entrada.
     */
    private ObjectInputStream ois;
    /**
     * Variable bandera para detener este hilo.
     */
    private boolean stop;
    /**
     * El cliente.
     */
    private Client client;

    public InputThread(Client cl) {
        try {
            this.client = cl;
            ois = new ObjectInputStream(cl.getSocket().getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Está atento a la respuesta del servidor. Si la respuesta es un
     * CommandResponse entonces lo pone el el buffer. Si la respuesta es un
     * MessageResponse entonces genera el evento.
     */
    @Override
    public void run() {
        try {
            client.setId(ois.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!stop) {
            try {
                final Object resp = ois.readObject();
                if (resp != null) {
                    if (resp instanceof CommandResponse) {
                        Buffer.setCommandResponse((CommandResponse) resp);
                    } else if (resp instanceof MessageResponse) {
                        for (final EventListener el : client.getEvents()) {
                            if (el instanceof MessageListener) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((MessageListener) el).messageReceived((MessageResponse) resp);
                                    }
                                }).start();
                            }
                        }
                    } else if (resp instanceof DisconnectResponse) {
                        stop();
                        Buffer.setCommandResponse(null);
                        client.getSocket().close();

                        for (EventListener el : client.getEvents()) {
                            if (el instanceof DisconnectListener) {
                                ((DisconnectListener) el).disconnectReceived((DisconnectResponse) resp);
                            }
                        }
                        client = null;
                    } else if (resp instanceof InformationResponse) {
                        for (final EventListener el : client.getEvents()) {
                            if (el instanceof InformationListener) {
                                /*Se larga un hilo para poder enviar datos también.*/
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((InformationListener) el).informationReceived((InformationResponse) resp);
                                    }
                                }).start();
                            }
                        }
                    } else if (resp instanceof ImageDifusion) {
                        for (final EventListener el : client.getEvents()) {
                            if (el instanceof ImageListener) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ImageListener) el).imageReceived((ImageDifusion) resp);
                                    }
                                }).start();
                            }
                        }
                    } else if (resp instanceof ObjectDiffusion) {
                        for (final EventListener el : client.getEvents()) {
                            if (el instanceof ObjectListener) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ObjectListener) el).objectReceived((ObjectDiffusion) resp);
                                    }
                                }).start();
                            }
                        }
                    }
                } else {
                }
            } catch (EOFException | SocketException e) {
                Logger.getLogger(InputThread.class.getName()).log(Level.INFO, "El server dejó de responder.", e);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     */
    void stop() {
        this.stop = true;
    }

    boolean isStopped() {
        return this.stop;
    }
}
