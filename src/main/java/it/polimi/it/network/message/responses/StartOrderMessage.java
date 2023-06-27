package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * StartOrder Message: sends to the TCP client the players' order.
 */
public class StartOrderMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = -3277232064866530527L;
    private ArrayList<String> order;

    public StartOrderMessage(ArrayList<String> order){
        this.order = order;
    }

    public ArrayList<String> getOrder() {
        return order;
    }
}
