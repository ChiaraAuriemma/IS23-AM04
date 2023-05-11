package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;


public class BoardUpdateMessage extends Payload implements Serializable {

    private static final long serialVersionUID = 888785574906121088L;
    private Tile[][] matrix;

    public BoardUpdateMessage(Tile[][] matrix){
        this.matrix = matrix;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }
}
