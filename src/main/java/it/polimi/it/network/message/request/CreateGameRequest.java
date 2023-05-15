package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class CreateGameRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = 6111690709070228972L;
    private String username;
    private int playerNumber;

    private ClientInterface client;


    public CreateGameRequest(String username, int playerNumber, ClientInterface client) {
        this.username = username;
        this.playerNumber = playerNumber;
        this.client = client;
    }

    public String getUsername() {
        return username;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public ClientInterface getClient() {
        return client;
    }
}
