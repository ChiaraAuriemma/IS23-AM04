package it.polimi.it.controller.Exceptions;

public class EmptyNicknameException extends Exception {
    public EmptyNicknameException(String message) {
        super(message);
    }
}
