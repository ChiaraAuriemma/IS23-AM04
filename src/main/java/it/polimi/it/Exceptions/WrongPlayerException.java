package it.polimi.it.Exceptions;


import java.io.Serializable;

/**
 * Exception that is thrown when a client tries to make an action when it's not its turn
 */
public class WrongPlayerException extends Exception implements Serializable {
    private static final long serialVersionUID = 6779708494747439575L;

    public WrongPlayerException(String message) {
        super(message);
    }
}
