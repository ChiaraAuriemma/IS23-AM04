package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;

public class JoinGameRequest extends Message {

    User user;
    int id;

    public JoinGameRequest(MessageType messageType, User user, int id) {
        super(messageType);
        this.user = user;
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }
}
