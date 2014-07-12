package executors.response;

import executors.Response;
import java.util.HashMap;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class MessageResponse extends Response<String>{
    public MessageResponse(){
        
    }
    
    public void addId(String id){
        super.addValue("id", id);
    }
    
    public String getId(){
        return super.getValue("id");
    }
    
    public void addText(String text){
        super.addValue("text", text);
    }
    
    public String getText(){
        return super.getValue("text");
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        MessageResponse resp = new MessageResponse();
        resp.mapa = (HashMap<String, String>) mapa.clone();
        return resp;
    }
}