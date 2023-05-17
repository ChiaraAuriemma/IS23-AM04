package it.polimi.it.network.message.request;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class SendChatMessage extends Payload implements Serializable {
    private static final long serialVersionUID = 7144777038369047737L;
    private String message;
    public SendChatMessage(String message) {
        this.message = message;
    }

    public String getChatMessage() {
        return message;
    }
}
