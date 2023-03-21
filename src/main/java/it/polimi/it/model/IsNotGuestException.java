package it.polimi.it.model;

public class IsNotGuestException extends Exception {
    private String Type;

    public IsNotGuestException(String Type){
        this.Type = Type;
    }

    public String returnType(){
        return Type;
    }
}
