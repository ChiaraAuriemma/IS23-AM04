package it.polimi.it.model;

public enum UserType {
    GUEST("GUEST"),
    PLAYER("PLAYER");

    private String Type;

    UserType(String Type){
        this.Type = "GUEST";
    }

    public String getType() {
        return Type;
    }

    public void setType() {
        Type = "PLAYER";
    }
}
