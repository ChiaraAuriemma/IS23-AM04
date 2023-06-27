package it.polimi.it.network.message.request;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * TCP message used for sending to the server the client's username when it connects
 */
public class LoginRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -8734863381644577654L;
    private String nickname;

    public LoginRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
