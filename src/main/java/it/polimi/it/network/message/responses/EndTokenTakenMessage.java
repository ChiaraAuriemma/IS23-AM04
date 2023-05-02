package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

public class EndTokenTakenMessage extends Payload {

    private User user;

    public EndTokenTakenMessage(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
