package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * StartTurn Message: notifies a TCP client that his turn just started.
 */
public class StartTurnMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 7334715792904290472L;
    private int maxValueofTiles;

    public StartTurnMessage(int maxValueofTiles){
        this.maxValueofTiles = maxValueofTiles;
    }

    public int getMaxValueofTiles() {
        return maxValueofTiles;
    }
}
