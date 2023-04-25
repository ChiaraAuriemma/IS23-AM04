package it.polimi.it.network.message;

import java.util.Optional;

public enum MessageType {
    CONNECTIONMESSAGE("CONNECTIONMESSAGE"),
    CREATEGAME("CREATEGAME"),
    CREATEPLAYER("CREATEPLAYER"),
    JOINGAME("JOINGAME"),
    PINGMESSAGE("PINGMESSAGE"),
    TILESNUMMESSAGE("TILESNUMMESSAGE"),
    TAKEABLETILES("TAKEABLETILES"),
    SELECTEDTILES("SELECTEDTILES"),
    CHOOSECOLUMN("CHOOSECOLUMN"),
    CHOOSEORDER("CHOOSEORDER"),
    ENDGAME("ENDGAME"),
    ERROR("ERROR"),
    INFO("INFO");


    private final String message;


    MessageType(String message) {
        this.message = message;
    }

    public String getMessageType(){
        return this.message;
    }








}
