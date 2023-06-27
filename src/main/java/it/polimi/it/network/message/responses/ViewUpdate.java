package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;


/**
 * ViewUpdate Message: forces a TCP client to update the view scene.
 */
public class ViewUpdate extends Payload  implements Serializable {

    private static final long serialVersionUID = 6130583668213481901L;
    String updateMessage;

    public ViewUpdate(){
        updateMessage = "va che mi sono aggiornatooooo";
    }

    public String getUpdateMessage() {
        return updateMessage;
    }
}
