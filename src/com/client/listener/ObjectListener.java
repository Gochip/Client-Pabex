package com.client.listener;

import com.difusion.ObjectDifusion;
import java.util.EventListener;

/**
 *
 * @author Parisi Germán
 */
public interface ObjectListener extends EventListener{
public void objectReceived(ObjectDifusion object);
}
