package it.polimi.it.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private static final long serialVersionUID = 8457851963563892967L;
    private List<String> chatList = new ArrayList<>();
    private List<String> currentChat = new ArrayList<>(9);


    /**
     * Constructor for the Chat class.
     */
    public Chat(){
        this.chatList = new ArrayList<>();
    }


    /**
     * Method that given a public
     * @param message , updates the chat list
     */
    public void newMessage(String message){
        String temp;
        while(message!=null){
            if(message.length()>=33){
                temp = message.substring(0, 32);
                temp = padTo33(temp);
                chatList.add(temp);
                temp=null;
                message = message.substring(33);
            }
            else {
                temp = padTo33(message);
                chatList.add(temp);
                message=null;
            }
        }
        updateChatDisplayer();
    }


    /**
     * Method that updates the list of the latest messages.
     */
    private void updateChatDisplayer() {
        int size = chatList.size();
        currentChat.clear();
        if(size>=9){
            currentChat = chatList.subList(size - 9, size);
        }else{
            for (int i =0; i<9-size; i++){
                String blankChatLine = "                                 ";
                currentChat.add(blankChatLine);
            }
            currentChat.addAll(chatList);
        }
    }


    /**
     * Getter method
     * @return the latest chat messages to be displayed.
     */
    public List<String> getCurrentChat(){
        updateChatDisplayer();
        return currentChat;
    }


    /**
     * Method that given a private
     * @param chatMessage , updates the chat list
     */
    public void newPrivateMessage(String chatMessage) {
        String temp;
        if(chatMessage.length()>=43){
            temp = chatMessage.substring(0, 42);
            temp = padTo43(temp);
            chatList.add(temp);
            temp=null;
            chatMessage = chatMessage.substring(43);
            while(chatMessage!=null){
                if(chatMessage.length()>=33){
                    temp = chatMessage.substring(0, 32);
                    temp = padTo33(temp);
                    chatList.add(temp);
                    temp=null;
                    chatMessage = chatMessage.substring(33);
                }
                else {
                    temp = padTo33(chatMessage);
                    chatList.add(temp);
                    chatMessage=null;
                }
            }
        }else{
            temp = padTo43(chatMessage);
            chatList.add(temp);
            chatMessage=null;
        }
        updateChatDisplayer();
    }


    /**
     * Method that pads the length of
     * @param message to 33, then
     * @return the padded message.
     */
    private String padTo33(String message) {
        while(message.length()<33){
            message = message + " ";
        }
        return message;
    }


    /**
     * Method that pads the length of
     * @param message to 43, then
     * @return the padded message.
     */
    private String padTo43(String message) {
        while(message.length()<43){
            message = message + " ";
        }
        return message;
    }
}
