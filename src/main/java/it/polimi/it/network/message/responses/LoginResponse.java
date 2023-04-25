package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;


public class LoginResponse extends Message {

    User user;

    public LoginResponse(MessageType messageType, User user) {
        super(messageType);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
