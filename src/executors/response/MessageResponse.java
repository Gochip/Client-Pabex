package executors.response;

import executors.Response;
import java.util.HashMap;

/**
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.1
 */
public class MessageResponse extends Response<String>{
    public MessageResponse(){
        
    }
    
    public void addId(String id){
        super.addValue("id", id);
    }
    
    public void addIdGroup(String id){
        super.addValue("id_group", id);
    }
    
    public String getId(){
        return super.getValue("id");
    }
    
    public String getIdGroup(){
        return super.getValue("id_group");
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