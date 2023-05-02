package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.util.List;

public class InitialMatrixMessage extends Payload {

    private Tile[][] matrix;

    public InitialMatrixMessage(Tile[][] matrix){
        this.matrix = matrix;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }

}
