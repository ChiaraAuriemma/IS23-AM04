package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.message.Payload;

public class CreateGameRequest extends Payload {

    User user;
    int playerNumber;

    ClientInterface client;


    public CreateGameRequest(User user, int playerNumber, ClientInterface client) {
        this.user = user;
        this.playerNumber = playerNumber;
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public ClientInterface getClient() {
        return client;
    }
}
