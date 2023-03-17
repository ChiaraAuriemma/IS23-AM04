package it.polimi.it.model;

import java.util.Scanner;

public class Lobby {

    private static String Nickname;

    public static String setNickname() {
        Scanner s = new Scanner(System.in);
        Nickname = s.nextLine();
        return Nickname;
    }

    void CreateUser(){
        User U = new User();
    }


}
