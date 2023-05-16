package it.polimi.it.model;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<String> chatList = new ArrayList<>();


    public Chat(){
        this.chatList = new ArrayList<>();
    }

    public void newMessage(String message){
        String temp = null;
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
    }

    private String padTo33(String message) {
        while(message.length()<33){
            message = message + " ";
        }
        return message;
    }
}
