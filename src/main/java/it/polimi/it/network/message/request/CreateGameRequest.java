package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

public class CreateGameRequest extends Payload {

    User user;
    int playerNumber;


    public CreateGameRequest(User user, int playerNumber) {
        this.user = user;
        this.playerNumber = playerNumber;
    }

    public User getUser() {
        return user;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
