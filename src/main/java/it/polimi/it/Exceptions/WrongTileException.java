package it.polimi.it.Exceptions;


import java.io.Serializable;

public class WrongTileException extends Exception implements Serializable {
    private static final long serialVersionUID = -4120016935230012609L;

    public  WrongTileException(String message) {
        super(message);
    }

}
