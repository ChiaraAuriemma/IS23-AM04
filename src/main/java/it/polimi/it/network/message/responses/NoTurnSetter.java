package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.awt.*;
import java.io.Serializable;

/**
 * NoTurnSetter Message: sets a TCP client's stage to NOTURN.
 */
public class NoTurnSetter extends Payload implements Serializable {

    private static final long serialVersionUID = 1935617357374678737L;

    private boolean a;

    public NoTurnSetter(boolean a){
        this.a=a;
    }

    public boolean getA(){
        return a;
    }
}
