package it.polimi.it.network.message.responses;

import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatUpdate extends Payload implements Serializable {
    private static final long serialVersionUID = 3624252035577253733L;
    private List<String> currChat = new ArrayList<>();
    public ChatUpdate(List<String> currChat) {
        this.currChat = currChat;
    }
    public List<String> getChatUpdate() {
        return currChat;
    }
}
