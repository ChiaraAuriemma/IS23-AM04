package it.polimi.it.network.message.request;


import it.polimi.it.network.message.Payload;

public class LoginRequest extends Payload {

    String nickname;

    public LoginRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
