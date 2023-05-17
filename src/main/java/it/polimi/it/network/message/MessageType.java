package it.polimi.it.network.message;

import java.io.Serializable;
import java.util.Optional;

public enum MessageType  implements Serializable {
    CONNECTIONMESSAGE("CONNECTIONMESSAGE"),
    CREATEGAME("CREATEGAME"),
    CREATEPLAYER("CREATEPLAYER"),
    CREATEPLAYERRESPONSE("CREATEPLAYERRESPONSE"),
    JOINGAME("JOINGAME"),
    PING("PING"),
    PONG("PONG"),
    TILESNUMMESSAGE("TILESNUMMESSAGE"),

    SELECTEDTILES("SELECTEDTILES"),
    CHOOSECOLUMN("CHOOSECOLUMN"),
    CHOOSEORDER("CHOOSEORDER"),
    ENDGAME("ENDGAME"),
    ERROR("ERROR"),
    INFO("INFO"),
    CREATEGAMERESPONSE("CREATEGAMERESPONSE"),


    //per lo start del game
    STARTORDERPLAYER("STARTORDERPLAYER"),
    INITIALMATRIX("INITIALMATRIX"),
    DRAWNCOMMONCARDS("DRAWNCOMMONCARDS"),
    DRAWNPERSONALCARD("DRAWNPERSONALCARD"),

    //durante il turno
    STARTTURN("STARTTURN"),
    TAKEABLETILES("TAKEABLETILES"),
    POSSIBLECOLUMNS("POSSIBLECOLUMNS"),

    //end turn
    SHELFIEUPDATE("SHELFIEUPDATE"),
    BOARDUPDATE("BOARDUPDATE"),
    POINTSUPDATE("POINTSUPDATE"),
    ENDTOKEN("ENDTOKEN"),
    UPDATEVIEW("UPDATEVIEW"),

    FINALPOINTS("FINALPOINTS"),
    JOINGAMERESPONSE("JOINGAMERESPONSE"),
    THISISNOTTHEDAY("THISISNOTTHEDAY"),


    CHAT("CHAT"),

    CHATUPDATE("CHATUPDATE");



    private final String message;


    MessageType(String message) {
        this.message = message;
    }

    public String getMessageType(){
        return this.message;
    }








}
