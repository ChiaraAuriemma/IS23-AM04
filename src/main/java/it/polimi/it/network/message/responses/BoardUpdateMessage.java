package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;


public class BoardUpdateMessage extends Payload implements Serializable {

    private static final long serialVersionUID = 888785574906121088L;
    private Tile[][] matrix;

    public BoardUpdateMessage(Tile[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                this.matrix[i][j] = new Tile(i,j, PossibleColors.valueOf(matrix[i][j].getColor()));
            }
        }
    }

    public Tile[][] getMatrix() {
        return matrix;
    }
}
