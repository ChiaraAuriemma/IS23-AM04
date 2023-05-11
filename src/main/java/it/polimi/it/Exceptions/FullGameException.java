package it.polimi.it.Exceptions;

import java.io.Serializable;

public class FullGameException extends Exception implements Serializable {
    private static final long serialVersionUID = 8165331967380944401L;

    public  FullGameException(String message) {
            super(message);
    }

}
