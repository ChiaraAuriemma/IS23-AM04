package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

public class JoinGameResponse extends Payload {

    int gameId;

    public JoinGameResponse(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
