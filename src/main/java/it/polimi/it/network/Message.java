package it.polimi.it.network;

public class Message {
    private final MessageType messageType;
    private final String message;

    public Message(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public MessageType getType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }
}
