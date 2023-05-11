package it.polimi.it.network.message.others;

import it.polimi.it.model.Game;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class ThisNotTheDay extends Payload  implements Serializable {
    private static final long serialVersionUID = -3294233920082178659L;
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
