package com.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parisi Germán & Barrionuevo Diego
 * @version 1.0
 */
public class InputThreadUDP implements Runnable {

    private byte[] buffer;
    private boolean bufferVacio;
    private DatagramSocket datagram;
    private static InputThreadUDP me;
    private InetAddress serverAddress;

    private InputThreadUDP(InetAddress serverAddress) {
        try {
            this.serverAddress = serverAddress;
            datagram = new DatagramSocket(6002);
            this.buffer = new byte[256];
        } catch (SocketException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static InputThreadUDP getInstance(InetAddress serverAddress) {
        if (me == null) {
            me = new InputThreadUDP(serverAddress);
        }
        return me;
    }

    private void receiveData() {
        try {
            DatagramPacket packet = new DatagramPacket(buffer, 256);
            datagram.receive(packet);
            bufferVacio = false;
        } catch (IOException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendData(String idGroup, String idClient, String data) {
        try {
            data = idGroup + "::" + idClient + "::" + data;
            byte[] b = new byte[256];
            byte[] bAux = data.getBytes(Charset.forName("UTF-8"));
            System.arraycopy(bAux, 0, b, 0, bAux.length);
            for (int i = bAux.length; i < 256; i++) {
                b[i] = 0;
            }
            DatagramPacket packet = new DatagramPacket(b, b.length, serverAddress, 6001);
            datagram.send(packet);
        } catch (UnknownHostException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            receiveData();
        }
    }

    public String getBuffer() {
        String s = new String(buffer, Charset.forName("UTF-8"));
        bufferVacio = true;
        return s;
    }

    public boolean isBufferEmpty() {
        return bufferVacio;
    }
}
