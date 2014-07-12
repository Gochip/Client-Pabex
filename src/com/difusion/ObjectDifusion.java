package com.difusion;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 *
 * @author Parisi Germán & Barrionuevo Diego
 * @version 1.0
 */
public class ObjectDifusion implements Serializable {

    private String groupId;
    private String senderId;
    private byte[] b;

    public ObjectDifusion(String groupId) {
        this.groupId = groupId;
    }

    public ObjectDifusion(String groupId, String senderId) {
        this.groupId = groupId;
        this.senderId = senderId;
    }

    public void setObject(ByteRepresentation object) {
        this.b = object.getBytes();
    }

    public byte[] getObject() {
        return b;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getSenderId() {
        return senderId;
    }
}
