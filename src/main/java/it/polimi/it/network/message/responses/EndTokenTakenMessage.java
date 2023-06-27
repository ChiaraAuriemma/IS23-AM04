package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;
import java.io.Serializable;

/**
 * EndToken Message: notifies a TCP client about which player completed his shelfie first.
 */
public class EndTokenTakenMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = -9088018950772537538L;
    private String username;

    public EndTokenTakenMessage(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
