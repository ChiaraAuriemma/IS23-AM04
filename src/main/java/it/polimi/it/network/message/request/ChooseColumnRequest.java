package it.polimi.it.network.message.request;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * TCP message used for sending to the server the client's column chosen
 */

public class ChooseColumnRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -1516091115924889631L;
    int columnNumber;

    public ChooseColumnRequest(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

}
