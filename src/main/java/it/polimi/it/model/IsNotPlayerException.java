package it.polimi.it.model;

public class IsNotPlayerException extends Exception {

    private String Type;

    public IsNotPlayerException(String Type){
        this.Type = Type;
    }

    public String returnType(){
        return Type;
    }
}