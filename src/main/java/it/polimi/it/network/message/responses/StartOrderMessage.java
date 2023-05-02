package it.polimi.it.network.message.responses;

import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.util.ArrayList;

public class StartOrderMessage extends Payload {

    ArrayList<User> order;

    public StartOrderMessage(ArrayList<User> order){
        this.order = order;
    }

    public ArrayList<User> getOrder() {
        return order;
    }
}
