package it.polimi.it.network.message.request;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * TCP message used for sending to the server the client's username, and it's choice of the game ID to join
 */
public class JoinGameRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -851442629157364654L;
    private String username;
    private int id;

    public JoinGameRequest(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getID() {
        return id;
    }

}
