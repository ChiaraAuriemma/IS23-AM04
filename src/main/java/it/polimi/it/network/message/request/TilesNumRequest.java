package it.polimi.it.network.message.request;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * TCP message used for sending to the server the client's choice of the number of tiles that it wants to take
 */
public class TilesNumRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -7710363129197514326L;
    private int numTiles;

    public TilesNumRequest(int numTiles) {
        this.numTiles = numTiles;
    }

    public int getNumTiles() {
        return numTiles;
    }
}
