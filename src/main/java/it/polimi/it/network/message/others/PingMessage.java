package it.polimi.it.network.message.others;

import it.polimi.it.network.message.Payload;

public class PingMessage extends Payload {
    String uselessMessage;

    public PingMessage(String message){
        uselessMessage=message;
    }
}
