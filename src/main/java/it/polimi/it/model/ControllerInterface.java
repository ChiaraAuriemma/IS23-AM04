package it.polimi.it.model;

import it.polimi.it.model.Board.Board;

import java.util.ArrayList;


public class ControllerInterface {

    private User user;
    private Game game;
   private Integer NumPlayer;
   private Board board;

   //metto gli attributi di collegamento con gli users e i flag a seconda degli user (tipo guest, creator, joiner, player)

   public ControllerInterface (){
       //add other ????????
   }

    public void CreateGameC (){
        this.game = new Game(NumPlayer);
        this.board = this.game.getBoard();
    }

    public ArrayList<Tile> ChooseTileC (){
        this.board.ChooseTile();//cosa passo a choosetile e cosa mi ritorna ???
        // ma soprattutto ... come la collego con play() di User ... parlo con albertone
    }


}
