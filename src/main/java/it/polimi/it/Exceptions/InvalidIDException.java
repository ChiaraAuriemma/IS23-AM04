package it.polimi.it.Exceptions;

import java.io.Serializable;

public class InvalidIDException extends Exception implements Serializable {
    private static final long serialVersionUID = 8708354800356069562L;

    public InvalidIDException(String message) {
        super(message);
    }
}
