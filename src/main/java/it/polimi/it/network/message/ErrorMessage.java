package it.polimi.it.network.message;

public class ErrorMessage extends Payload{

    String error;

    public ErrorMessage(String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
