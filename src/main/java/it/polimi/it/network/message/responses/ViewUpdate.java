package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

public class ViewUpdate extends Payload {

    String updateMessage;

    public ViewUpdate(){
        updateMessage = "va che mi sono aggiornatooooo";
    }

    public String getUpdateMessage() {
        return updateMessage;
    }
}
