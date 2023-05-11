package it.polimi.it.Exceptions;


import java.io.Serializable;

public class ExistingNicknameException extends Exception implements Serializable {
    private static final long serialVersionUID = -3278750535062255210L;

    public ExistingNicknameException(String message) {
        super(message);
    }
}
