package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;

public class CreateGameRequest extends Message {

    User user;
    int playerNumber;


    public CreateGameRequest(MessageType messageType, User user, int playerNumber) {
        super(messageType);
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
