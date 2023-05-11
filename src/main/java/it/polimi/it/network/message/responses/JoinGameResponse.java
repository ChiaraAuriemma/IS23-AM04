package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class JoinGameResponse extends Payload  implements Serializable {

    private static final long serialVersionUID = 246798386683788344L;
    int gameId;

    public JoinGameResponse(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
