package com.client;

import com.difusion.Diffusion;
import executors.response.CommandResponse;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import senders.CreateGroup;

/**
 * Clase que hace la conexión con el servidor.
 *
 * Se aplica el patrón Singleton.
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.2
 */
public class Client {

    private String frameworkPassword;
    private String id;
    /**
     * Se usa para el patrón Singleton.
     */
    private static Client me;
    /**
     * Es la lista de eventos. Cada elemento de esta lista es un Escuchador de
     * Mensaje (MessageListener)
     */
    private ArrayList<EventListener> events;
    /**
     * Hilo escuchador TCP.
     */
    private InputThread inputThread;
    /**
     * Hilo escuchado UDP.
     */
    private InputThreadUDP udp;
    /**
     * Canal de comunicación.
     */
    private Socket s;
    /**
     * Flujo de salida.
     */
    private ObjectOutputStream oos;

    /**
     *
     * @param ip la ip del servidor.
     * @param puerto es el puerto de entrada en el servidor.
     * @throws UnknownHostException no se encontró la ip del servidor.
     * @throws IOException no pudo crear el flujo de salida.
     */
    private Client(String ip, int puerto, String pass) throws UnknownHostException, IOException {
        this.frameworkPassword = pass;
        s = new Socket(ip, puerto);
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeUTF(frameworkPassword);
        oos.flush();
        events = new ArrayList<>();
        inputThread = new InputThread(this);
        Thread t = new Thread(inputThread, "EscuchadorTCP");
        t.start();

    }

    /**
     * Método estático público para obtener siempre la misma instancia de
     * Client.
     *
     * @param ip la ip del servidor.
     * @param puerto es el puerto de entrada en el servidor.
     * @return siempre la misma instancia de esta clase (Patrón Singleton).
     * @throws UnknownHostException no se encontró la ip del servidor.
     * @throws IOException no pudo crear el flujo de salida.
     */
    public static Client getInstance(String ip, int puerto, String pass) throws UnknownHostException, IOException {
        assert ip != null;
        if (me == null) {
            me = new Client(ip, puerto, pass);
        }
        return me;
    }

    /**
     * Envía una cadena al servidor. Debe haberse ya conectado.
     *
     * La cadena que se envía debe estar codificada en ISO-8859-1 (latin-1).
     *
     * @param c es la cadena a enviar al servidor.
     * @return la respuesta del servidor envuelta en un CommandResponse. Retorna
     * <code>null</code> en caso que se haya generado una IOException.
     */
    public synchronized CommandResponse send(Serializable c) {
        //assert c != null;
        try {
            if(c instanceof Diffusion){
                ((Diffusion)c).setIdSender(id);
            }
            oos.writeObject(c);
            oos.flush();
            while (Buffer.getCommandResponse() == null && !inputThread.isStopped()) {
            }
            if (inputThread.isStopped()) {
                return null;
            }

            CommandResponse resp = (CommandResponse) Buffer.getCommandResponse().clone();
            Buffer.setCommandResponse(null);
            return resp;

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Se lanzó alguna excepción.
        return null;
    }

    /**
     * Desconecta el cliente del servidor.
     *
     *
     * @return un CommandRsponse.
     */
    public CommandResponse disconnect() {
        return send("DISCONNECT");
    }

    /**
     * Agregar un escuchador para el evento de llegada de un mensaje.
     *
     * @param ml es el escuchador de llegada de mensaje.
     */
    public void addEventListener(EventListener ml) {
        events.add(ml);
    }

    public String getId() {
        return this.id;
    }
    
    /**
     *
     * @return el socket de comunicación.
     */
    Socket getSocket() {
        return this.s;
    }

    /**
     *
     * @return la lista de eventos.
     */
    public ArrayList<EventListener> getEvents() {
        return this.events;
    }

    void setId(String id) {
        this.id = id;
    }

    /**
     * Este método inicializa la configuración para UDP.
     * Envía el puerto de escucha UDP al servidor.
     */
    public void initUDP() {
        int listenPort = 6002;
        initUDP(listenPort);
    }
    
    /**
     * Este método inicializa la configuración para UDP.
     * Envía el puerto de escucha UDP al servidor.
     * @param listenPort es el puerto de escucha.
     */
    public void initUDP(int listenPort) {
        send("CONFIGURE_UDP '" + listenPort + "'");
        udp = new InputThreadUDP(s.getInetAddress(), this, listenPort);
        new Thread(udp).start();
    }

    /**
     * Envia datos a un grupo por UDP.
     * 
     * Debe llamar antes a initUDP.
     * @param idGroup es el id del grupo al que se va a enviar.
     * @param data son los datos a enviar.
     */
    public void sendUDP(String idGroup, String data) {
        udp.sendData(idGroup, id, data);
    }

    public CommandResponse sendTextToGroup(String groupId, String text) {
        return send("MESSAGE_GROUP '" + groupId + "' '" + text + "'");
    }

    public CommandResponse sendTextToClient(String clientId, String text) {
        return send("MESSAGE_CLIENT '" + clientId + "' '" + text + "'");
    }

    public CommandResponse createGroup(CreateGroup createGroup) {
        return send("CREATE_GROUP " + createGroup.toString());
    }

    public CommandResponse createGroup(String groupName) {
        return send("CREATE_GROUP '" + groupName + "'");
    }

    public CommandResponse join(String groupId) {
        return send("JOIN '" + groupId + "'");
    }

    public CommandResponse join(String groupId, String password) {
        return send("JOIN '" + groupId + "' -p '" + password + "'");
    }

    public CommandResponse leaveGroup(String groupId) {
        return send("LEAVE_GROUP '" + groupId + "'");
    }

    public CommandResponse showGroups() {
        return send("SHOW_GROUPS");
    }

    public CommandResponse showGroup(String groupId) {
        return send("SHOW_GROUP '" + groupId + "'");
    }
}
