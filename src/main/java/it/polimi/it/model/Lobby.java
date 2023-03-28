package it.polimi.it.model;

import java.util.ArrayList;

public class Lobby {

    private static String nickname;

    private static ArrayList<User> userList;

    private Game game;

    public Lobby() {
        userList = new ArrayList<>();
    }

    /*public static String setNickname() {
        Scanner s = new Scanner(System.in);
        nickname = s.nextLine();
        return nickname;
    }
    */
    void createUser(String nickname){
        User user = new User(nickname);
        userList.add(user);
    }

    public void createGame(User user, int playerNumber) {

        pickUser(user);

        this.game = new Game(playerNumber, user);
    }

    public void joinGame(User user) {

        pickUser(user);

    }

    public static void pickUser(User user){
        userList.remove(user);
    }


}
