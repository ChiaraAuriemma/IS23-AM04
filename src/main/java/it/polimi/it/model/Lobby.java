package it.polimi.it.model;

import java.util.ArrayList;

public class Lobby {

    private ArrayList<User> userList;
    private Game game;

    public Lobby() {
        userList = new ArrayList<User>();
    }

    User createUser(String nickname){
        User user = new User(nickname);
        userList.add(user);

        return user;
    }

    void createGame(User user, int playerNumber) {

        pickUser(user);

        this.game = new Game(playerNumber, user);
    }

    void joinGame(User user) {

        pickUser(user);

    }

    void pickUser(User user){
        userList.remove(user);
    }

    ArrayList<User> getUserList(){
        return this.userList;
    }

    Game getGame(){
        return this.game;
    }


}
