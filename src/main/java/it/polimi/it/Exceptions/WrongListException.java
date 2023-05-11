package it.polimi.it.Exceptions;

import java.io.Serializable;

public class WrongListException  extends Exception implements Serializable {
    private static final long serialVersionUID = 3529506918824290656L;

    public WrongListException(String message) {
        super(message);
    }
}
