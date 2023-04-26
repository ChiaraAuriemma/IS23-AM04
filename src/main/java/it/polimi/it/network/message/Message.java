package it.polimi.it.network.message;

import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -5546544051959622771L;
    // private  static final long serialVersionUID = ;

    private final MessageType messageType;
    private Payload payload;

    public Message(MessageType messageType, Payload payload) {
        this.messageType = messageType;
        this.payload = payload;
    }

    public MessageType getType() {
        return messageType;
    }

    public Payload getPayload() {
        return payload;
    }
}