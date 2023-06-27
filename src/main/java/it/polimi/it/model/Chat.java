package it.polimi.it.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private static final long serialVersionUID = 8457851963563892967L;
    private List<String> currentChat;


    /**
     * Constructor for the Chat class.
     */
    public Chat(){
        this.currentChat = new ArrayList<>(9);
    }


    /**
     * Method that given a public
     * @param message , updates the chat list
     */
    public void newMessage(String message){
       currentChat.add(message);
    }



    /**
     * Getter method
     * @return the latest chat messages to be displayed.
     */
    public List<String> getCurrentChat(){
        return currentChat;
    }


    /**
     * Method that given a private
     * @param chatMessage , updates the chat list
     */
    public void newPrivateMessage(String chatMessage) {
        currentChat.add(chatMessage);
    }


}
