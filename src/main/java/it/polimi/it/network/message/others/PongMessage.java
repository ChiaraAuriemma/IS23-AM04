package it.polimi.it.network.message.others;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;


/**
 * Pong Message: empty message which is used for confirming that a TCP client is till online.
 */
public class PongMessage extends Payload implements Serializable {

    private static final long serialVersionUID = 8446349514013464404L;

    String uselessMessage;

    public PongMessage(String message){
        uselessMessage = message;
    }
}
