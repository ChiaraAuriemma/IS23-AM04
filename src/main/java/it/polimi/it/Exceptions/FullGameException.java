package it.polimi.it.Exceptions;

import java.io.Serializable;

/**
 * Exception that is thrown when a game is full and a client wants to join that game
 */
public class FullGameException extends Exception implements Serializable {
    private static final long serialVersionUID = 8165331967380944401L;

    public  FullGameException(String message) {
            super(message);
    }

}
