package it.polimi.it.network.message;

import java.io.Serializable;


/**
 * Error Message: contains a string that explains the error that has just occurred.
 */
public class ErrorMessage extends Payload implements Serializable {
    private static final long serialVersionUID = 3495485248352174367L;
    String error;


    public ErrorMessage(String error){
        this.error = error;
    }


    public String getError() {
        return error;
    }
}
