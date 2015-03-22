package com.difusion;

/**
 *
 * @author Parisi Germán
 */
public class ObjectDiffusion extends Diffusion {

    private byte[] b;

    public ObjectDiffusion(String idGroup) {
        super(idGroup);
    }

    public void setObject(ByteRepresentation object) {
        this.b = object.getBytes();
    }

    public byte[] getObject() {
        return b;
    }
}
