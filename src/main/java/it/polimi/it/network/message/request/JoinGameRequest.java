package it.polimi.it.network.message.request;

import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

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
