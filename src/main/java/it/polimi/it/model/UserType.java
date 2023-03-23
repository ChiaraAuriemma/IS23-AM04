package it.polimi.it.model;

public enum UserType {
    GUEST("GUEST"),
    PLAYER("PLAYER");

    private String type;

    UserType(String Type){
        this.type = "GUEST";
    }

    public String getType() {
        return type;
    }

    public void setType() {
        type = "PLAYER";
    }
}
