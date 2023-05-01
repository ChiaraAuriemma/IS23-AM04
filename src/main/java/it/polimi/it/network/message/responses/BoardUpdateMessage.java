package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

public class BoardUpdateMessage extends Payload {

    private Tile[][] matrix;

    public BoardUpdateMessage(Tile[][] matrix){
        this.matrix = matrix;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }
}
