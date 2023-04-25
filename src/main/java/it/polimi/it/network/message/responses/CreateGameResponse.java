package it.polimi.it.network.message.responses;


import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;

public class CreateGameResponse extends Message {

    int gameId;

    public CreateGameResponse(MessageType messageType, int gameId) {
        super(messageType);
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
