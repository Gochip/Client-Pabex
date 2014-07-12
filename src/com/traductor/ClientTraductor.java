/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.traductor;

/**
 *
 * @author PaBex
 * @version 
 */
public class ClientTraductor {
private String id,name,ip;

    public ClientTraductor(String id, String name, String ip) {
        this.id = id;
        this.name = name;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "ClientTraductor{" + "id=" + id + ", name=" + name + ", ip=" + ip + '}';
    }

}
