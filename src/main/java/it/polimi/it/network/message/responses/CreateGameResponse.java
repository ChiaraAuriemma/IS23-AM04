package it.polimi.it.network.message.responses;




import it.polimi.it.network.message.Payload;

public class CreateGameResponse extends Payload {

    int gameId;

    public CreateGameResponse(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
