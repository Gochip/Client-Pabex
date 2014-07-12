package executors.response;

import executors.Response;
import java.util.HashMap;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class InformationResponse extends Response<String> {

    public InformationResponse() {

    }

    public void addId(String id) {
        super.addValue("id", id);
    }

    public String getId() {
        return super.getValue("id");
    }

    public void addSource(String source) {
        super.addValue("source", source);
    }

    public String getSource() {
        return super.getValue("source");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        InformationResponse resp = new InformationResponse();
        resp.mapa = (HashMap<String, String>) mapa.clone();
        return resp;
    }
}
