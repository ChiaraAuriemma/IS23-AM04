package it.polimi.it.network.message.errors;

import it.polimi.it.network.message.Payload;

public class InvalidIDError extends Payload {

    private String error;
    public  InvalidIDError (String error){
        this.error=error;
    }

    public String getError() {
        return error;
    }
}
