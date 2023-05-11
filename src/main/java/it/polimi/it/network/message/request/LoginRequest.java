package it.polimi.it.network.message.request;


import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class LoginRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -8734863381644577654L;
    String nickname;

    public LoginRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
