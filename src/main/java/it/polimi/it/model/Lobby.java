package it.polimi.it.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Lobby {

    private static String nickname;

    private static ArrayList<String> guestList;

    /*public static String setNickname() {
        Scanner s = new Scanner(System.in);
        Nickname = s.nextLine();
        return Nickname;
    }
    */
    void createUser(){
        User U = new User();
    }

    public void setGuestList(String nickname){
        guestList.add(nickname);
    }

    public static String pickGuest(){
        return guestList.remove(0);
    }


}
