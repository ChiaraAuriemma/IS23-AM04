package it.polimi.it.model;

import java.util.ArrayList;

public class Lobby {

    private static String nickname;

    private static ArrayList<String> guestList;

    public Lobby() {
        guestList = new ArrayList<>();
    }

    /*public static String setNickname() {
        Scanner s = new Scanner(System.in);
        nickname = s.nextLine();
        return nickname;
    }
    */
    void createUser(){
        User U = new User();
        guestList.add(nickname);
    }

    public static void pickGuest(){
        guestList.remove(0);
    }


}
