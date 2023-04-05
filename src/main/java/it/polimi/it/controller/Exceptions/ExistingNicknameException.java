package it.polimi.it.controller.Exceptions;

public class ExistingNicknameException extends Exception {
    public ExistingNicknameException(String message) {
        super(message);
    }
}
