package it.polimi.it.model;

import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class Guest implements UserType {
    private Game game;

    UserType PlayerState;

    @Override
    public List<List<Tile>> choosableTiles(int tilesNum) {
        return null;
    }

    @Override
    public boolean[] chooseSelectedTiles(List<Tile> choosen) {
        return null;
    }

    @Override
    public void inserTile(int column, String[] colorOrder) {

    }

    @Override
    public String getNickname() {
        return null;
    }
    @Override
    public int getScore() {
        return 0;
    }
    @Override
    public boolean checkInGame() {
        return false;
    }

    @Override
    public void createGame(String nickname, int playerNumber, User user) {

        Lobby.pickGuest();

        //this.game = new Game(playerNumber, this);

        user.setPlayerType(PlayerState);
    }

    @Override
    public void joinGame(String nickname, User user) {

        Lobby.pickGuest();

        user.setPlayerType(PlayerState);

    }

    @Override
    public Shelfie createShelfie(int personalCardID) {
        return null;
    }

    @Override
    public int maxValueofTiles() {
        return 0;
    }
}
