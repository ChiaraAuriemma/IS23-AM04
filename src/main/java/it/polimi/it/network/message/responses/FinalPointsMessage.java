package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * FinalPoints Message: notifies a TCP client about the last points in order to print the final leaderboard.
 */
public class FinalPointsMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 5907269673872489026L;
    private List<String> usernames;
    private ArrayList<Integer> points;

    public FinalPointsMessage(List<String> usernames, ArrayList<Integer> points){
        this.usernames = usernames;
        this.points = points;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }
}
