package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class InitialMatrixMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = -4940233933985265764L;
    private Tile[][] matrix;

    public InitialMatrixMessage(Tile[][] matrix){
        this.matrix = matrix;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }

}
