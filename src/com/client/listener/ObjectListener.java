package com.client.listener;

import com.difusion.ObjectDiffusion;
import java.util.EventListener;

/**
 *
 * @author Parisi Germán
 */
public interface ObjectListener extends EventListener{
public void objectReceived(ObjectDiffusion object);
}
