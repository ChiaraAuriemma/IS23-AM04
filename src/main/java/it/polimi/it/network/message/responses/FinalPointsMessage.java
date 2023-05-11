package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FinalPointsMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 5907269673872489026L;
    private List<User> users;
    private ArrayList<Integer> points;

    public FinalPointsMessage(List<User> users, ArrayList<Integer> points){
        this.users = users;
        this.points = points;
    }

    public List<User> getUsers() {
        return users;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }
}
