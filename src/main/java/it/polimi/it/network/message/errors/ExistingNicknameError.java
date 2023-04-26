package it.polimi.it.network.message.errors;

import it.polimi.it.network.message.Payload;

public class ExistingNicknameError extends Payload {

    private String error;
    public  ExistingNicknameError (String error){
        this.error=error;
    }

    public String getError() {
        return error;
    }
}
