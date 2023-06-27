package it.polimi.it.network.message.request;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * TCP message used for sending to the server the client's username, and it's choice of the players number in that game
 */
public class CreateGameRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = 6111690709070228972L;
    private String username;
    private int playerNumber;


    public CreateGameRequest(String username, int playerNumber) {
        this.username = username;
        this.playerNumber = playerNumber;

    }

    public String getUsername() {
        return username;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }


}
