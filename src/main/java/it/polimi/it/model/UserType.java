package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public interface UserType {

    public List<List<Tile>> choosableTiles(int tilesNum);

    public boolean[] chooseSelectedTiles(List<Tile> choosen);

    public void inserTile(int column, List<Tile> choosen);

    public boolean checkInGame(User user);

    void createGame(String Nickname, int playerNumber, User user);

    void joinGame(String Nickname, User User);

    Shelfie createShelfie(int personalCardID);

    public int maxValueofTiles();

    public Shelfie getShelf(User user);

    public Board getBoard(User user);
}
