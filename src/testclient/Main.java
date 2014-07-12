package testclient;

import com.client.Client;
import com.client.listener.DisconnectListener;
import com.client.listener.InformationListener;
import com.client.listener.MessageListener;
import executors.response.CommandResponse;
import executors.response.DisconnectResponse;
import executors.response.InformationResponse;
import executors.response.MessageResponse;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import receivers.Interpreter;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class Main {

    public static void main(String args[]) {
        try {
            Client cl = Client.getInstance("localhost", 20202, "123");
            
            cl.addEventListener(new MessageListener() {
                @Override
                public void messageReceived(MessageResponse response) {
                    System.out.println("RECIBISTE UN MENSAJE-----------------");
                    System.out.println("DE (ID): " + response.getId() + "\n");
                    System.out.println("TEXTO: " + response.getText() + "\n\n");
                    System.out.println("-------------------------------------");
                }
            });

            cl.addEventListener(new DisconnectListener() {
                @Override
                public void disconnectReceived(DisconnectResponse response) {
                    System.out.println("Desconectado....");
                    System.exit(0);
                }
            });
            
            cl.addEventListener(new InformationListener() {

                @Override
                public void informationReceived(InformationResponse response) {
                    Interpreter interpreter = new Interpreter(response);
                    if(interpreter.isJoin()){
                        System.out.println("Se unio un cliente: " + response.getId());
                    }else if(interpreter.isDisconect()){
                        System.out.println("Se desconecto un cliente: " + response.getId());
                    }else if(interpreter.isLeaveGroup()){
                        System.out.println("Un cliente abandono el grupo con id: " + response.getId());
                        System.out.println(interpreter.isDeletedGroup());
                    }else if(interpreter.isAddAttribute()){
                        System.out.println("Hubo un cambio de atributo del cliente " + response.getId());
                    }
                }
            });

            Scanner s = new Scanner(System.in, "ISO-8859-1");

            String linea = "";
            while (true) {
                System.out.print("# ");
                linea = s.nextLine();
                if (linea.equalsIgnoreCase("DISCONNECT")) {
                    break;
                } else if (linea.equals("id")) {
                    System.out.println(cl.getId());
                    continue;
                }
                CommandResponse resp = cl.send(linea);
                String clave = "";

                if (resp == null) {
                    System.out.println("Se acabó el tiempo de espera...");
                    continue;
                }
                while (true) {
                    System.out.print("(MODO MAPA)# ");
                    clave = s.nextLine();
                    if (clave.equals("keys")) {
                        System.out.println(resp.getKeys());
                    } else if (clave.equals("exit")) {
                        break;
                    } else {
                        System.out.println("");
                        System.out.println(clave + ": " + resp.getOption(clave));
                        System.out.println("------------------------------------");
                    }
                }
            }
            cl.disconnect();

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
