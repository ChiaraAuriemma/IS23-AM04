package it.polimi.it.model;

import org.junit.After;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void UserTest(){
        User u = new User("Alby");
        Shelfie shelf = u.createShelfie();

        assertNotNull(u);

        assertNotNull(shelf);

    }
}
