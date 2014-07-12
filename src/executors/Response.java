package executors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * La clase padre de cada Respuesta del servidor a los clientes
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.1
 */
public class Response<T extends java.io.Serializable> implements java.io.Serializable, Cloneable {

    protected HashMap<String, T> mapa;

    public Response() {
        mapa = new HashMap<>();
    }

    protected void addValue(String k, T v) {
        mapa.put(k, v);
    }

    public T getValue(String k) {
        return mapa.get(k);
    }

    public int getCount() {
        return mapa.size();
    }

    public Set<String> getKeys() {
        return mapa.keySet();
    }

    public Collection<T> getValues() {
        return mapa.values();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Response ne = new Response();
        ne.mapa = (HashMap<String, String>) mapa.clone();
        return ne;
    }
}
