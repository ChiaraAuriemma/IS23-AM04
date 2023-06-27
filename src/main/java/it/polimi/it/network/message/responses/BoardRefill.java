package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * BoardRefill Message: notifies a TCP client that the LivingRoom Board was refilled.
 */
public class BoardRefill extends Payload implements Serializable {

    private static final long serialVersionUID = 479614382488698786L;

    public BoardRefill(){

    }

}
