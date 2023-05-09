package it.polimi.it.network.message.others;

import it.polimi.it.model.Game;
import it.polimi.it.network.message.Payload;

public class ThisNotTheDay extends Payload {
    private Game game;
    private int gameID;

    public ThisNotTheDay(Game game, int gameID){
        this.game=game;
        this.gameID=gameID;
    }

    public Game getGame() {
        return game;
    }

    public int getGameID() {
        return gameID;
    }
}
