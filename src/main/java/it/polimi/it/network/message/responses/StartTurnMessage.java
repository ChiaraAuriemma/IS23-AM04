package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

public class StartTurnMessage extends Payload {

    private int maxValueofTiles;

    public StartTurnMessage(int maxValueofTiles){
        this.maxValueofTiles = maxValueofTiles;
    }

    public int getMaxValueofTiles() {
        return maxValueofTiles;
    }
}
