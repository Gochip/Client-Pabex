/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.listener;


import executors.response.MessageResponse;
import java.util.EventListener;

/**
 * Escuchador para los eventos de llegada de mensaje.
 * @author PaBex
 */
public interface MessageListener extends EventListener{
    public void messageReceived(MessageResponse response);
}
