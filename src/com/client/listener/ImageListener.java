/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.client.listener;

import com.difusion.ImageDifusion;
import java.util.EventListener;

/**
 *
 * @author PaBex
 * @version 
 */
public interface ImageListener extends EventListener{
public void imageReceived(ImageDifusion image);
}
