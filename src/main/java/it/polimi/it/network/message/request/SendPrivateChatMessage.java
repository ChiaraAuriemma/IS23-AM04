package it.polimi.it.network.message.request;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * TCP message used for sending to the server the client's username, the private chat message that it wants to send,
 * and the receiver of the message
 */
public class SendPrivateChatMessage extends Payload implements Serializable {

    private static final long serialVersionUID = -8355507561580485249L;
    private String message;

    private String receiver;

    private String sender;
    public SendPrivateChatMessage(String sender, String chatMessage, String receiver) {
        this.message = chatMessage;
        this.receiver=receiver;
        this.sender=sender;
    }

    public String getChatMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }
}
