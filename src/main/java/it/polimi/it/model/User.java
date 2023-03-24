package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class User {

    private UserType type;
    private static String nickname;

    private final int score;

    private final boolean inGame;

    int tilesNumber;

    private Game game;

    private Shelfie shelf;

    private Board board;

    public User(int score, boolean inGame){

        //nickname = Lobby.setNickname();

        this.score = 0;

        this.inGame = true;

        this.type = new Guest();
    }

    public void setGuestType(UserType GuestState){
        this.type = GuestState;
    }

    public void setPlayerType(UserType PlayerState){
        this.type = PlayerState;
    }
    public int maxValueOfTiles(){
        return type.maxValueofTiles();
    }

    public List<List<Tile>> choosableTiles(int tilesNum){
        return type.choosableTiles(tilesNum);
    }

    public boolean[] chooseSelectedTiles(List<Tile> choosen){
        return type.chooseSelectedTiles(choosen);
    }

    public void inserTile(int column, String[] colorOrder){
        type.inserTile(column, colorOrder);
    }

    public String getNickname(){
        return type.getNickname();
    }

    public int getScore(){
        return type.getScore();
    }

    public boolean checkInGame(){
        return type.checkInGame();
    }


    public void createGame(String nickname, int playerNumber){
        type.createGame(nickname, playerNumber, this);
    }

    public void joinGame(String nickname, User User){
        type.joinGame(nickname, this);
    }

    public Shelfie createShelfie(int personalCardID){

        return type.createShelfie(personalCardID);
    }
}
