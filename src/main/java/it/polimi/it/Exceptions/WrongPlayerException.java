package it.polimi.it.Exceptions;


import java.io.Serializable;

public class WrongPlayerException extends Exception implements Serializable {
    private static final long serialVersionUID = 6779708494747439575L;

    public WrongPlayerException(String message) {
        super(message);
    }
}
