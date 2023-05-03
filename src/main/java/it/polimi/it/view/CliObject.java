package it.polimi.it.view;

public enum CliObject{

    //metto le tile con i colori



    private String object;

    CliObject(String object){
        this.object = object;
    }

    public String getObject(){
        return this.object;
    }
}
