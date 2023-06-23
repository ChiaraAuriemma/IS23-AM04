package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

public class RestartMessage extends Payload {

    String message;

    public RestartMessage(){
        this.message = "restart";
    }

    public String getMessage() {
        return message;
    }
}
