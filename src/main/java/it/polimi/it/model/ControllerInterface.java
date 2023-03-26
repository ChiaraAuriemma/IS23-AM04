package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.Tile;

import java.util.ArrayList;


public class ControllerInterface {

    private User user;

    private Integer numPlayer;

    public ControllerInterface (String nickname){
        // prima controllo in lobby se il nome esiste già
       this.user = new User(nickname);
   }

    public void createGameC (int numPlayer){
        this.numPlayer = numPlayer;
        this.user.createGame(this.user, this.numPlayer);
    }

    public void joinGameC (){
       this.user.joinGame();
    }

    public void choosableTilesC (int tilesValue){
       this.user.choosableTiles (tilesValue);
    }

    //troppi elementiiiiiiiii passati per parametroooooo
    public void chooseSelectedTilesC (int x1, int x2, int x3, int y1, int y2, int y3, String col1, String col2, String col3){
       this.user.ChooseSelectedTiles(x1,x2,x3,y1,y2,y3,col1,col2,col3);
    }

    public void insertTileC (int column, String[] colorOrder){
       this.user.insertTile (column, colorOrder);
    }

}
