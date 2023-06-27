package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;


/**
 * Restart Message: asks a TCP client whether he wants to play again after a finished match.
 */
public class RestartMessage extends Payload {

    String message;

    public RestartMessage(){
        this.message = "restart";
    }

    public String getMessage() {
        return message;
    }
}
