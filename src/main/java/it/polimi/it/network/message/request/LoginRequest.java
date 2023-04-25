package it.polimi.it.network.message.request;


import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;

public class LoginRequest extends Message {

    String nickname;

    public LoginRequest(MessageType messageType,String nickname) {
        super(messageType);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
