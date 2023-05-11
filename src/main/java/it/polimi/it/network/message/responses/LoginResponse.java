package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;


public class LoginResponse extends Payload  implements Serializable {

    private static final long serialVersionUID = 2751440945579989375L;
    User user;

    public LoginResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
