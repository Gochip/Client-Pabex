/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client;

import executors.response.CommandResponse;

/**
 * Buffer de CommandResponse. Mantiene la referencia a un objeto de
 * CommandRepsonse.
 *
 * @author PaBex
 * @version 0.1
 */
class Buffer {

    private static CommandResponse resp = null;

    public static synchronized void setCommandResponse(CommandResponse cr) {
        resp = cr;
    }

    public static synchronized CommandResponse getCommandResponse() {
        return resp;
    }
}