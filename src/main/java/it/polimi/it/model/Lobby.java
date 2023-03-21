package it.polimi.it.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Lobby {

    private static String Nickname;

    private static ArrayList<String> GuestList;

    /*public static String setNickname() {
        Scanner s = new Scanner(System.in);
        Nickname = s.nextLine();
        return Nickname;
    }
    */
    void CreateUser(){
        User U = new User();
    }

    public void setGuestList(String Nickname){
        GuestList.add(Nickname);
    }

    public static String PickGuest(){
        return GuestList.remove(0);
    }


}
