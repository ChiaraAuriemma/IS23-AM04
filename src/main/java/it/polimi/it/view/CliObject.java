package it.polimi.it.view;

public enum CliObjectType {

    //metto le tile con i colori



    private String object;

    CliObjectType(String object){
        this.object = object;
    }

    public String getObject(){
        return this.object;
    }
}
