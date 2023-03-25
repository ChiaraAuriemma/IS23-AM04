package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class User {

    private UserType type;
    private static String nickname;

    private int score;

    private final boolean inGame;

    int tilesNumber;

    private Game game;

    private Shelfie shelf;

    private Board board;

    public User(){

        //nickname = Lobby.setNickname();

        this.score = 0;

        this.inGame = true;

        this.type = new Guest();
    }

    public void setGuestType(UserType type){
        this.type = type;
    }

    public void setPlayerType(UserType type){
        this.type = type;
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

    public void inserTile(int column, List<Tile> choosen){
        type.inserTile(column, choosen);
    }

    public String getNickname(){
        return nickname;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int val){
        score = val;
    }

    public boolean checkInGame(User user){
        return type.checkInGame(this);
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

    public Shelfie getShelf(User user){
        return type.getShelf(this);
    }

    public Board getBoard(User user){
        return type.getBoard(this);
    }
}
