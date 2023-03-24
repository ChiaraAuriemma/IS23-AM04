package it.polimi.it.model;

import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public interface UserType {

    public List<List<Tile>> choosableTiles(int tilesNum);

    public boolean[] chooseSelectedTiles(List<Tile> choosen);

    public void inserTile(int column, String[] colorOrder);

    public String getNickname();

    public int getScore();

    public boolean checkInGame();

    void createGame(String Nickname, int playerNumber, User user);

    void joinGame(String Nickname, User User);

    Shelfie createShelfie(int personalCardID);

    public int maxValueofTiles();
}
