package test;

import com.client.Client;
import com.client.listener.MessageListener;
import executors.response.CommandResponse;
import executors.response.MessageResponse;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parisi Germán
 */
public class MainUDP {

    public static void main(String[] args) {
        try {
            Client cl = Client.getInstance("localhost", 20202, "123");
            cl.initUDP(6004);
            
            cl.addEventListener(new MessageListener() {

                @Override
                public void messageReceived(MessageResponse response) {
                    System.out.println(response.getText());
                }
            });
            
            Scanner sc = new Scanner(System.in);
            System.out.println("Creador (yes|no): ");
            String linea = sc.nextLine();
            if (linea.equals("yes")) {
                CommandResponse cr = cl.send("CREATE_GROUP 'g'");
            } else {
                cl.send("JOIN '000'");
                cl.sendUDP("000", "Holaaaa");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MainUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
