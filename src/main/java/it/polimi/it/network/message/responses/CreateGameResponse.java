package it.polimi.it.network.message.responses;




import it.polimi.it.network.message.Payload;

import java.io.Serializable;


public class CreateGameResponse extends Payload  implements Serializable{

    private static final long serialVersionUID = -1080327236567829974L;
    int gameId;

    public CreateGameResponse(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
