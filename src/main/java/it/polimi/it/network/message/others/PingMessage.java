package it.polimi.it.network.message.others;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;


public class PingMessage extends Payload implements Serializable {
    private static final long serialVersionUID = 4804856223671484139L;
    String uselessMessage;

    public PingMessage(String message){
        uselessMessage=message;
    }
}
