package it.polimi.it.Exceptions;


import java.io.Serializable;

/**
 * Exception that is thrown when the client's nickname already exist in the lobby
 */
public class ExistingNicknameException extends Exception implements Serializable {
    private static final long serialVersionUID = -3278750535062255210L;

    public ExistingNicknameException(String message) {
        super(message);
    }
}
