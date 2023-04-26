package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;


public class LoginResponse extends Payload {

    User user;

    public LoginResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
