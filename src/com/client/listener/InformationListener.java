/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.client.listener;

import executors.response.InformationResponse;
import java.util.EventListener;

/**
 *
 * @author PaBex
 * @version 
 */
public interface InformationListener  extends EventListener{
    public void informationReceived(InformationResponse response);
}
