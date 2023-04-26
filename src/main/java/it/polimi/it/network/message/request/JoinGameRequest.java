package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

public class JoinGameRequest extends Payload {

    User user;
    int id;

    public JoinGameRequest(User user, int id) {
        this.user = user;
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public int getID() {
        return id;
    }
}
