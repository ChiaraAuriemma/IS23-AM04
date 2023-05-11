package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class EndTokenTakenMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = -9088018950772537538L;
    private User user;

    public EndTokenTakenMessage(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
