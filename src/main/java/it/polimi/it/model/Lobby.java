package it.polimi.it.model;

import it.polimi.it.model.Exception.NotExistingUser;

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

    void createGame(User user, int playerNumber) throws IndexOutOfBoundsException, NotExistingUser {

        if(playerNumber < 1 || playerNumber > 4){
            throw new IndexOutOfBoundsException();
        }

        if(userList.size()==0){
            throw new NotExistingUser("Non c'è nessun utente che può creare la partita");
        }

        pickUser(user);

        this.game = new Game(playerNumber, user);
    }

    void joinGame(User user) throws NotExistingUser{

        if(userList.size()==0){
            throw new NotExistingUser("Non c'è nessun utente che può unirsi alla partita");
        }
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
