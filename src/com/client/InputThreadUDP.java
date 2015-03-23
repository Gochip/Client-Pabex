package com.client;

import com.client.listener.MessageListener;
import executors.response.MessageResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parisi Germán y Barrionuevo Diego
 * @version 1.1
 */
public class InputThreadUDP implements Runnable {

    /**
     * Es el datagrama que se usa para enviar y recibir datos a través de UDP.
     */
    private DatagramSocket datagram;
    /**
     * Dirección del servidor.
     */
    private InetAddress serverAddress;
    /**
     * Puerto en el cual el cliente recibe las respuestas.
     */
    private int listenPort;
    /**
     * Puerto en el cual el servidor recibe las peticiones.
     */
    private int serverPort = 6001;
    /**
     * Es el cliente.
     */
    private Client client;
    /**
     * Separador que usa para enviar datos UDP.
     */
    public static String SEPARATOR_UDP = "::";

    /**
     * 
     * @param serverAddress es la dirección del servidor.
     * @param client es el cliente.
     */
    InputThreadUDP(InetAddress serverAddress, Client client, int listenPort) {
        try {
            this.serverAddress = serverAddress;
            this.client = client;
            this.listenPort = listenPort;
            datagram = new DatagramSocket(listenPort);
        } catch (SocketException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            receiveData();
        }
    }
    
    private void receiveData() {
        try {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, 256);
            datagram.receive(packet);
            String cadena = new String(packet.getData(), Charset.forName("UTF-8"));
            final MessageResponse resp = new MessageResponse();
            resp.addText(cadena);
            resp.addId("-1");
            for (final EventListener el : client.getEvents()) {
                if (el instanceof MessageListener) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((MessageListener) el).messageReceived(resp);
                        }
                    }).start();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Envía datos a través de UDP.
     * @param idGroup es el id del grupo al cual enviar.
     * @param idClient es el id del cliente que envía.
     * @param data son los datos que se desea enviar.
     */
    void sendData(String idGroup, String idClient, String data) {
        try {
            data = idGroup + SEPARATOR_UDP + idClient + SEPARATOR_UDP + data;
            byte[] b = new byte[256];
            byte[] bAux = data.getBytes(Charset.forName("UTF-8"));
            System.arraycopy(bAux, 0, b, 0, bAux.length);
            for (int i = bAux.length; i < 256; i++) {
                b[i] = 0;
            }
            DatagramPacket packet = new DatagramPacket(b, b.length, serverAddress, serverPort);
            datagram.send(packet);
        } catch (UnknownHostException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
