package it.polimi.it.network.message;

public class Message {
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
