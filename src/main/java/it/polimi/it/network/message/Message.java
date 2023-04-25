package it.polimi.it.network.message;


public class Message {
    private final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getType() {
        return messageType;
    }

}
