package test;

import com.client.Client;
import com.client.listener.ObjectListener;
import com.difusion.ByteRepresentation;
import com.difusion.ObjectDiffusion;
import executors.response.CommandResponse;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parisi German
 */
public class MainDifusion {

    public static void main(String[] args) {
        try {
            Client cl = Client.getInstance("localhost", 20202, "123");
            cl.addEventListener(new ObjectListener() {

                @Override
                public void objectReceived(ObjectDiffusion object) {
                    byte b[] = object.getObject();
                    for(int i = 0; i < b.length; i++){
                        System.out.println(b[i]);
                    }
                }
            });

            Scanner sc = new Scanner(System.in);
            System.out.println("Creador (yes|no): ");
            String linea = sc.nextLine();
            if (linea.equals("yes")) {
                CommandResponse cr = cl.send("CREATE_GROUP 'g'");
            } else {
                CommandResponse cr = cl.send("JOIN '000");

                ObjectDiffusion object = new ObjectDiffusion("000");
                object.setObject(new ByteRepresentation() {

                    private byte b[];

                    @Override
                    public byte[] getBytes() {
                        if(b == null){
                            b = new byte[]{1, 2, 3, 4};
                        }
                        return b;
                    }

                    @Override
                    public void setBytes(byte[] b) {
                        this.b = b;
                    }
                });
                cl.send(object);
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainDifusion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
