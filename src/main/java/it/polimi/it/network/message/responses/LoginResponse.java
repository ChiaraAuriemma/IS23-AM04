package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;
import java.io.Serializable;

/**
 * LoginResponse Message: notifies a TCP client that his login was successful.
 */
public class LoginResponse extends Payload  implements Serializable {

    private static final long serialVersionUID = 2751440945579989375L;
    private String username;

    public LoginResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
