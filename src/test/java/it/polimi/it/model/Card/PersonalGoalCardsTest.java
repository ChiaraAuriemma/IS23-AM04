package it.polimi.it.model.Card;

import com.google.gson.Gson;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;
import it.polimi.it.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonalGoalCardsTest {

    private PersonalGoalCard personalGoalCard1;
    private PersonalGoalCard personalGoalCard2;
    private PersonalGoalCard personalGoalCard3;
    private PersonalGoalCard personalGoalCard4;
    private PersonalGoalCard personalGoalCard5;
    private PersonalGoalCard personalGoalCard6;
    private PersonalGoalCard personalGoalCard7;
    private PersonalGoalCard personalGoalCard8;
    private PersonalGoalCard personalGoalCard9;
    private PersonalGoalCard personalGoalCard10;
    private PersonalGoalCard personalGoalCard11;
    private PersonalGoalCard personalGoalCard12;

    private Shelfie shelfie;
    private TilesBag bag;
    private User u = new User(" ");

    @Before
    public void card(){
        this.personalGoalCard1 = new PersonalGoalCard(1);
        this.personalGoalCard2 = new PersonalGoalCard(2);
        this.personalGoalCard3 = new PersonalGoalCard(3);
        this.personalGoalCard4 = new PersonalGoalCard(4);
        this.personalGoalCard5 = new PersonalGoalCard(5);
        this.personalGoalCard6 = new PersonalGoalCard(6);
        this.personalGoalCard7 = new PersonalGoalCard(7);
        this.personalGoalCard8 = new PersonalGoalCard(8);
        this.personalGoalCard9 = new PersonalGoalCard(9);
        this.personalGoalCard10 = new PersonalGoalCard(10);
        this.personalGoalCard11 = new PersonalGoalCard(11);
        this.personalGoalCard12 = new PersonalGoalCard(12);
        this.shelfie = new Shelfie(u);
        this.bag = new TilesBag();
    }

    @Test
    public void personalCard1initialization(){  //card 1
        assertEquals(personalGoalCard1.getPinkposColumn(),0);
        assertEquals(personalGoalCard1.getPinkposRow(),5);
        assertEquals(personalGoalCard1.getCyanposColumn(),2);
        assertEquals(personalGoalCard1.getCyanposRow(),0);
        assertEquals(personalGoalCard1.getYellowposColumn(),1);
        assertEquals(personalGoalCard1.getYellowposRow(),2);
        assertEquals(personalGoalCard1.getBlueposColumn(),2);
        assertEquals(personalGoalCard1.getBlueposRow(),5);
        assertEquals(personalGoalCard1.getWhiteposColumn(),3);
        assertEquals(personalGoalCard1.getWhiteposRow(),3);
        assertEquals(personalGoalCard1.getGreenposColumn(),4);
        assertEquals(personalGoalCard1.getGreenposRow(),4);
    }
    @Test
    public void personalCard12initialization(){ //card12
        assertEquals(personalGoalCard12.getPinkposColumn(),1);
        assertEquals(personalGoalCard12.getPinkposRow(),4);
        assertEquals(personalGoalCard12.getCyanposColumn(),3);
        assertEquals(personalGoalCard12.getCyanposRow(),2);
        assertEquals(personalGoalCard12.getYellowposColumn(),4);
        assertEquals(personalGoalCard12.getYellowposRow(),1);
        assertEquals(personalGoalCard12.getBlueposColumn(),2);
        assertEquals(personalGoalCard12.getBlueposRow(),3);
        assertEquals(personalGoalCard12.getWhiteposColumn(),2);
        assertEquals(personalGoalCard12.getWhiteposRow(),5);
        assertEquals(personalGoalCard12.getGreenposColumn(),0);
        assertEquals(personalGoalCard12.getGreenposRow(),0);
    }

    @Test
    public void checkScoreTest1(){ //card1
        int column, row;
        Tile [][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }

        matrix[5][0] = new Tile(5, 0,PossibleColors.PINK);
        matrix[0][2] = new Tile(0, 2,PossibleColors.CYAN);
        matrix[2][1] = new Tile(2, 1,PossibleColors.YELLOW);
        matrix[5][2] = new Tile(5, 2,PossibleColors.BLUE);
        matrix[3][3] = new Tile(3, 3,PossibleColors.WHITE);
        matrix[4][4] = new Tile(4, 4,PossibleColors.GREEN);

        shelfie.setShelf(matrix);

        if(personalGoalCard1.checkScore(shelfie) != 6)
            fail("Test failed");
    }

    @Test
    public void checkScoreTest2(){ //card1
        int column, row;
        Tile [][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }

        matrix[5][0] = new Tile(5, 0,PossibleColors.CYAN);
        matrix[0][2] = new Tile(0, 2,PossibleColors.PINK);
        matrix[2][1] = new Tile(2, 1,PossibleColors.WHITE);
        matrix[5][2] = new Tile(5, 2,PossibleColors.GREEN);
        matrix[3][3] = new Tile(3, 3,PossibleColors.BLUE);
        matrix[4][4] = new Tile(4, 4,PossibleColors.YELLOW);

        shelfie.setShelf(matrix);

        if(personalGoalCard1.checkScore(shelfie) != 0)
            fail("Test failed");
    }

    @Test
    public void checkScoreTest3(){ //card1
        int column, row;
        Tile [][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }

        matrix[5][0] = new Tile(5, 0,PossibleColors.PINK);
        matrix[0][2] = new Tile(0, 2,PossibleColors.YELLOW);
        matrix[2][1] = new Tile(2, 1,PossibleColors.CYAN);
        matrix[5][2] = new Tile(5, 2,PossibleColors.BLUE);
        matrix[3][3] = new Tile(3, 3,PossibleColors.GREEN);
        matrix[4][4] = new Tile(4, 4,PossibleColors.GREEN);

        shelfie.setShelf(matrix);

        if(personalGoalCard1.checkScore(shelfie) != 3)
            fail("Test failed");
    }

    @Test
    public void checkScoreTest4(){ //card12
        int column, row;
        Tile [][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }

        matrix[4][1] = new Tile(4, 1,PossibleColors.PINK);
        matrix[2][3] = new Tile(2, 3,PossibleColors.CYAN);
        matrix[1][4] = new Tile(1, 4,PossibleColors.YELLOW);
        matrix[3][2] = new Tile(3, 2,PossibleColors.BLUE);
        matrix[5][2] = new Tile(5, 2,PossibleColors.WHITE);
        matrix[0][0] = new Tile(0, 0,PossibleColors.GREEN);

        shelfie.setShelf(matrix);

        if(personalGoalCard12.checkScore(shelfie) != 6)
            fail("Test failed");
    }

    @Test
    public void checkScoreTest5(){ //card6
        int column, row;
        Tile [][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }

        matrix[0][0] = new Tile(4, 1,PossibleColors.PINK);
        matrix[5][2] = new Tile(2, 3,PossibleColors.CYAN);
        matrix[1][1] = new Tile(1, 4,PossibleColors.YELLOW);
        matrix[1][3] = new Tile(3, 2,PossibleColors.BLUE);
        matrix[3][3] = new Tile(5, 2,PossibleColors.WHITE);
        matrix[5][4] = new Tile(0, 0,PossibleColors.GREEN);

        shelfie.setShelf(matrix);

        if(personalGoalCard6.checkScore(shelfie) != 6)
            fail("Test failed");
    }

    @Test
    public void checkScoreTest6(){ //card6
        int column, row;
        Tile [][] matrix = new Tile[6][5];
        matrix[0][0] = new Tile(0, 0, PossibleColors.DEFAULT);
        matrix[1][0] = new Tile(1,0,PossibleColors.DEFAULT);
        matrix[2][0] = new Tile(2,0,PossibleColors.DEFAULT);
        matrix[3][0] = new Tile(3, 0,PossibleColors.DEFAULT);
        matrix[4][0] = new Tile(4,0,PossibleColors.DEFAULT);
        matrix[5][0] = new Tile(5,0,PossibleColors.DEFAULT);

        matrix[0][1] = new Tile(0, 1, PossibleColors.DEFAULT);
        matrix[1][1] = new Tile(1,1,PossibleColors.DEFAULT);
        matrix[2][1] = new Tile(2,1,PossibleColors.DEFAULT);
        matrix[3][1] = new Tile(3, 1,PossibleColors.DEFAULT);
        matrix[4][1] = new Tile(4,1,PossibleColors.DEFAULT);
        matrix[5][1] = new Tile(5,1,PossibleColors.DEFAULT);

        matrix[0][2] = new Tile(0, 2, PossibleColors.DEFAULT);
        matrix[1][2] = new Tile(1,2,PossibleColors.DEFAULT);
        matrix[2][2] = new Tile(2,2,PossibleColors.DEFAULT);
        matrix[3][2] = new Tile(3, 2,PossibleColors.DEFAULT);
        matrix[4][2] = new Tile(4,2,PossibleColors.DEFAULT);
        matrix[5][2] = new Tile(5,2,PossibleColors.DEFAULT);

        matrix[0][3] = new Tile(0, 3, PossibleColors.DEFAULT);
        matrix[1][3] = new Tile(1,3,PossibleColors.DEFAULT);
        matrix[2][3] = new Tile(2,3,PossibleColors.DEFAULT);
        matrix[3][3] = new Tile(3, 3,PossibleColors.DEFAULT);
        matrix[4][3] = new Tile(4,3,PossibleColors.DEFAULT);
        matrix[5][3] = new Tile(5,3,PossibleColors.DEFAULT);

        matrix[0][4] = new Tile(0, 4, PossibleColors.DEFAULT);
        matrix[1][4] = new Tile(1,4,PossibleColors.DEFAULT);
        matrix[2][4] = new Tile(2,4,PossibleColors.DEFAULT);
        matrix[3][4] = new Tile(3, 4,PossibleColors.DEFAULT);
        matrix[4][4] = new Tile(4,4,PossibleColors.DEFAULT);
        matrix[5][4] = new Tile(5,4,PossibleColors.DEFAULT);


        shelfie.setShelf(matrix);

        if(personalGoalCard6.checkScore(shelfie) != 0)
            fail("Test failed");
    }
}
