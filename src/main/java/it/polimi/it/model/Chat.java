package it.polimi.it.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat implements Serializable {
    private static final long serialVersionUID = 8457851963563892967L;
    private List<String> chatList = new ArrayList<>();

    private List<String> currentChat = new ArrayList<>(9);
    private final String blankChatLine = "                                 ";


    public Chat(){
        this.chatList = new ArrayList<>();
    }

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

    private void updateChatDisplayer() {
        int size = chatList.size();
        currentChat.clear();
        if(size>=9){
            currentChat = chatList.subList(size - 9, size);
        }else{
            for (int i =0; i<9-size; i++){
                currentChat.add(blankChatLine);
            }
            currentChat.addAll(chatList);
        }
    }

    private String padTo33(String message) {
        while(message.length()<33){
            message = message + " ";
        }
        return message;
    }


    public List<String> getCurrentChat(){
        updateChatDisplayer();
        return currentChat;
    }
}
