package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class Guest implements UserType {
    private Game game;

    @Override
    public List<List<Tile>> choosableTiles(int tilesNum) {
        return null;
    }

    @Override
    public boolean[] chooseSelectedTiles(List<Tile> choosen) {
        return null;
    }

    @Override
    public void inserTile(int column, List<Tile> choosen) {

    }
    @Override
    public boolean checkInGame(User user) {
        return false;
    }

    @Override
    public void createGame(String nickname, int playerNumber, User user) {

        Lobby.pickGuest();

        this.game = new Game(playerNumber, user);

        user.setPlayerType(new Player());
    }

    @Override
    public void joinGame(String nickname, User user) {

        Lobby.pickGuest();

        user.setPlayerType(new Player());

    }

    @Override
    public Shelfie createShelfie(int personalCardID) {
        return null;
    }

    @Override
    public int maxValueofTiles() {
        return 0;
    }

    @Override
    public Shelfie getShelfie(User user){
        return null;
    }

    @Override
    public Board getBoard(User user){
        return null;
    }
}
