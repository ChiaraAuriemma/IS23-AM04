package it.polimi.it.network;

import java.util.Optional;

public enum MessageType {
    CONNECTIONMESSAGE("Starting connection"),
    CREATEGAME("Creation command"),
    JOINGAME("join request"),
    PINGMESSAGE("Ping"),
    TILESNUMMESSAGE("Number of tiles"),
    TAKEABLETILES("List of tiles"),
    SELECTEDTILES("selected tiles"),
    CHOOSECOLUMN("selected column"),
    CHOOSEORDER("order of the players"),
    ENDGAME("Iron man dies"),
    ERROR(""),
    INFO("");


    private final String message;


    MessageType(String message) {
        this.message = message;

    }








}
