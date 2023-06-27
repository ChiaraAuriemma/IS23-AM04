package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;


/**
 * CreateGameResponse Message: notifies a TCP client that he successfully created a new game.
 */
public class CreateGameResponse extends Payload  implements Serializable{

    private static final long serialVersionUID = -1080327236567829974L;
    private int gameId;

    public CreateGameResponse(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
