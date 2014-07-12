/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.listener;


import executors.response.DisconnectResponse;
import java.util.EventListener;

/**
 * Escuchador para los eventos de llegada de mensaje.
 * @author PaBex
 */
public interface DisconnectListener extends EventListener{
    public void disconnectReceived(DisconnectResponse response);
}
