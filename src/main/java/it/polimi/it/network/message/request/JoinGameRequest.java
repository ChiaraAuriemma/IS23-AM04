package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class JoinGameRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -851442629157364654L;
    User user;
    int id;

    ClientInterface client;

    public JoinGameRequest(User user, int id, ClientInterface client) {
        this.user = user;
        this.id = id;
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public int getID() {
        return id;
    }

    public ClientInterface getClient() {
        return client;
    }
}
