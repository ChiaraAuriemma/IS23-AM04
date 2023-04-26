package it.polimi.it.network.message.errors;

import it.polimi.it.network.message.Payload;

public class WrongPlayerError extends Payload {

    private String error;
    public  WrongPlayerError (String error){
        this.error=error;
    }

    public String getError() {
        return error;
    }
}
