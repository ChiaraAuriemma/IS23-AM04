package it.polimi.it.model.Card;

import com.google.gson.Gson;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonalGoalCardsTest {

    private PersonalGoalCard personalGoalCard;
    private Shelfie shelfie;
    private TilesBag bag;

    @Before
    public void card(){
        this.personalGoalCard = new PersonalGoalCard(1);
        this.shelfie = new Shelfie();
        this.bag = new TilesBag();
    }

    @Test
    public void personalCard1initialization(){  //card 1
        assertEquals(personalGoalCard.getPinkposColumn(),0);
        assertEquals(personalGoalCard.getPinkposRow(),5);
        assertEquals(personalGoalCard.getCyanposColumn(),2);
        assertEquals(personalGoalCard.getCyanposRow(),0);
        assertEquals(personalGoalCard.getYellowposColumn(),1);
        assertEquals(personalGoalCard.getYellowposRow(),2);
        assertEquals(personalGoalCard.getBlueposColumn(),2);
        assertEquals(personalGoalCard.getBlueposRow(),5);
        assertEquals(personalGoalCard.getWhiteposColumn(),3);
        assertEquals(personalGoalCard.getWhiteposRow(),3);
        assertEquals(personalGoalCard.getGreenposColumn(),4);
        assertEquals(personalGoalCard.getGreenposRow(),4);
    }
    @Test
    public void personalCard12initialization(){ //card12
        assertEquals(personalGoalCard.getPinkposColumn(),1);
        assertEquals(personalGoalCard.getPinkposRow(),4);
        assertEquals(personalGoalCard.getCyanposColumn(),3);
        assertEquals(personalGoalCard.getCyanposRow(),2);
        assertEquals(personalGoalCard.getYellowposColumn(),4);
        assertEquals(personalGoalCard.getYellowposRow(),1);
        assertEquals(personalGoalCard.getBlueposColumn(),2);
        assertEquals(personalGoalCard.getBlueposRow(),3);
        assertEquals(personalGoalCard.getWhiteposColumn(),2);
        assertEquals(personalGoalCard.getWhiteposRow(),5);
        assertEquals(personalGoalCard.getGreenposColumn(),0);
        assertEquals(personalGoalCard.getGreenposRow(),0);
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

        if(personalGoalCard.checkScore(shelfie) != 6)
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

        if(personalGoalCard.checkScore(shelfie) != 0)
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

        if(personalGoalCard.checkScore(shelfie) != 3)
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

        if(personalGoalCard.checkScore(shelfie) != 6)
            fail("Test failed");
    }
}
