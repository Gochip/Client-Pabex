/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traductor;

import com.traductor.exceptions.InvalidKeyException;
import com.traductor.exceptions.NotExistsGroupException;
import com.client.Client;
import com.difusion.ImageDifusion;
import com.traductor.exceptions.DuplicateInGroupException;
import com.traductor.exceptions.GroupIsFullException;
import com.traductor.exceptions.NotExistsClientException;
import executors.response.CommandResponse;
import java.util.ArrayList;

/**
 *
 * @author PaBex
 * @version
 */
public class TraductorController {

    private static TraductorController me;
    private Client client;

    private TraductorController(Client c) {
        this.client = c;
    }

    public static TraductorController getInstance(Client c) {
        if (me == null) {
            me = new TraductorController(c);
        }
        return me;
    }

    public String createGroup(String nameGroup) {
        return createGroup(nameGroup, null, null);
    }

    /**
     *
     * @param nameGroup
     * @param pass clave para conectarse al grupo
     * @param maxNum Máxima cantidad de clientes en el grupo, si * * * * * *      * es <code>null</code> por defecto 8.
     * @return Id del grupo creado.
     */
    public String createGroup(String nameGroup, String pass, Integer maxNum) {
        StringBuilder sb = new StringBuilder("CREATE_GROUP ");
        sb.append("'").append(nameGroup).append("' ");
        if (pass != null) {
            sb.append("-p '").append(pass).append("' ");
        }
        if (maxNum != null) {
            sb.append("-n '").append(maxNum).append("'");
        }
        CommandResponse cr = client.send(sb.toString());
        return cr.getOption("id_group");
    }

    /**
     *
     * @param idGroup Id del grupo a conectar
     * @param pass Clave para conectarse al grupo si es que tiene, * * * * *
     * sino <code>null</code>
     * @return <code>true</code> si se unió correctamente, caso contrario
     * <code>false</code>
     */
    public boolean joinToGroup(String idGroup, String pass) {
        boolean ok = false;
        if (idGroup == null || idGroup.equals("")) {
            return ok;
        }
        StringBuilder sb = new StringBuilder("JOIN ").append("'").append(idGroup).append("' ");
        if (pass != null) {
            sb.append("-p '").append(pass).append("' ");
        }
        CommandResponse cr = client.send(sb.toString());
        if (cr.getOption("error") == null) {
            ok = true;
        }
        return ok;
    }

    /**
     * Este método devuelve las siguientes excepciones:<p>
     * NotExistsGroupException</p>
     * <p>InvalidKeyException</p>
     * <p>GroupIsFullException</p>
     * <p>DuplicateInGroupException</p>
     * <p>NullPointerException</p>
     *
     * @param idGroup Id del grupo a conectar
     * @param pass <p>Clave para conectarse al grupo si es que tiene </p>
     * <p>sino <code>null</code></p>
     *
     *
     */
    public void joinToGroupE(String idGroup, String pass) {
        if (idGroup == null || idGroup.trim().equals("")) {
            throw new NullPointerException("Id de Grupo vacío o nulo");
        }
        StringBuilder sb = new StringBuilder("JOIN ").append("'").append(idGroup).append("' ");
        if (pass != null) {
            sb.append("-p '").append(pass).append("' ");
        }

        CommandResponse cr = client.send(sb.toString());
        if (cr.getOption("error") != null) {
        } else {
            String err = cr.getOption("error");
            switch (err) {
                case "1":
                    throw new NotExistsGroupException(cr.getOption("error_info"));
                case "3":
                    throw new GroupIsFullException(cr.getOption("error_info"));
                case "4":
                    throw new DuplicateInGroupException(cr.getOption("error_info"));
                case "5":
                case "6":
                    throw new InvalidKeyException(cr.getOption("error_info"));

            }
        }

    }

    /**
     *
     * @param receiver Cliente destinatario al cual va dirigido el mensaje.
     * @param text El mensaje
     * @return
     */
    public boolean sendMessageToClient(String receiver, String text) {
        boolean ok = false;
        if (receiver == null || receiver.trim().equals("") || text == null || text.trim().equals("")) {
            return ok;
        }
        StringBuilder sb = new StringBuilder("MESSAGE_CLIENT ");
        sb.append("'").append(receiver).append("'");
        sb.append("'").append(text).append("'");
        CommandResponse cr = client.send(sb.toString());
        String err = cr.getOption("error");
        if (err == null) {
            ok = true;
        }
        return ok;
    }

    /**
     * Este método devuelve las siguientes excepciones:
     * <p>NotExistsClientException</p>
     * <p>NullPointerException</p>
     *
     * @param receiver
     * @param text
     */
    public void sendMessageToClientE(String receiver, String text) {

        if (receiver == null || receiver.trim().equals("") || text == null || text.trim().equals("")) {
            throw new NullPointerException("Destinatario o texto --> nulo o vacío");
        }
        StringBuilder sb = new StringBuilder("MESSAGE_CLIENT ");
        sb.append("'").append(receiver).append("'");
        sb.append("'").append(text).append("'");
        CommandResponse cr = client.send(sb.toString());
        String err = cr.getOption("error");
        if (err != null) {
        } else {
            switch (err) {
                case "7":
                    throw new NotExistsClientException(cr.getOption("error_info"));
            }
        }

    }

    /**
     *
     * @param idGroup ID del grupo
     * @param text Mensaje a enviar a los integrantes del grupo
     * @param excluds Array con los id de clientes excluidos(No recibirán el
     * mensaje enviado)
     * @return true si fue enviado correctamente
     */
    public boolean sendMessageGroup(String idGroup, String text, ArrayList<String> excluds) {
        boolean ok = false;
        if (idGroup == null || idGroup.trim().equals("") || text == null || text.trim().equals("")) {
            return ok;
        }
        StringBuilder sb = new StringBuilder("MESSAGE_GROUP ");
        sb.append("'").append(idGroup).append("'");
        sb.append("'").append(text).append("'");

        if (excluds != null && !excluds.isEmpty()) {
            sb.append("-e '");
            for (String idC : excluds) {
                sb.append(idC).append(',');
            }
            sb.append("'");
        }
        CommandResponse cr = client.send(sb.toString());

        String err = cr.getOption("error");
        if (err == null) {
            ok = true;
        }
        return ok;
    }

    /**
     * Este método puede lanzar las siguientes excepciones:
     * <p>NullPointerException</p>
     * <p>NotExistsGroupException</p>
     * <p>NotExistsClientException</p>
     *
     * @param idGroup
     * @param text
     * @param excluds
     */
    public void sendMessageGroupE(String idGroup, String text, ArrayList<String> excluds) {
        if (idGroup == null || idGroup.trim().equals("") || text == null || text.trim().equals("")) {
            throw new NullPointerException("IdGroup o Text---> null | empty");
        }
        StringBuilder sb = new StringBuilder("MESSAGE_GROUP ");
        sb.append("'").append(idGroup).append("'");
        sb.append("'").append(text).append("'");

        if (excluds != null && !excluds.isEmpty()) {
            sb.append("-e '");
            for (String idC : excluds) {
                sb.append(idC).append(',');
            }
            sb.append("'");
        }
        CommandResponse cr = client.send(sb.toString());

        String err = cr.getOption("error");
        if (err != null) {
        } else {
            switch (err) {
                case "1":
                    throw new NotExistsGroupException(cr.getOption("error_info"));

                case "2":
                    throw new NotExistsClientException(cr.getOption("error_info"));
            }
        }

    }

    /**
     *
     * @return un array con TODOS los grupos que estan en el Servidor. Vacio si
     * no hay grupos
     */
    public ArrayList<TraductorGroup> getGroups() {
        ArrayList<TraductorGroup> groups = new ArrayList<>();
        CommandResponse cr = client.send("SHOW_GROUPS");
        String idGroups = cr.getOption("id_groups");
        if (idGroups != null) {
            String[] ids = idGroups.trim().split(",");
            for (String idGrupo : ids) {
                String name = cr.getOption(idGrupo + "_group_name");
                String idAdmin = cr.getOption(idGrupo + "_id_admin");
                Integer maxNum = Integer.parseInt(cr.getOption(idGrupo + "_max_num"));
                groups.add(new TraductorGroup(idGrupo, name, idAdmin, maxNum));
            }
        }
        return groups;
    }

    public void disconnect() {
        client.disconnect();
    }

    public ArrayList<ClientTraductor> getClientsOfGroup(String idGroup) {
        ArrayList<ClientTraductor> clientes = new ArrayList<>();
        if (idGroup == null || idGroup.equals("")) {
            throw new NullPointerException("El Id del grupo es vacío o nulo");
        }
        CommandResponse cr = client.send("SHOW_GROUP '" + idGroup+"'");
        String err = cr.getOption("error");
        if (err != null) {
            switch (err) {
                case "1":
                    throw new NotExistsGroupException(cr.getOption("error_info"));
            }
        }
        String idClients = cr.getOption("id_clients");
        String[] ids = idClients.trim().split(",");
        for (String idCliente : ids) {
            String name = cr.getOption(idCliente + "_name");
            String ip = cr.getOption(idCliente + "_ip");
            clientes.add(new ClientTraductor(idCliente, name, ip));
        }

        return clientes;
    }
    
    public int sendImage(String url,String idGroup){
        int count = -1;
        ImageDifusion img = new ImageDifusion(idGroup);
        img.setImage(url);
        CommandResponse cr = client.send(img);
        String err = cr.getOption("error");
        if(err != null){
            count = Integer.parseInt(cr.getOption("count"));
        }
        return count;
    }
}
