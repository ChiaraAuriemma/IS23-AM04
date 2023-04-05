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
    public void personalCard1initialization(){
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
    public void checkScoreTest1(){
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
            fail("Test fallito");
    }
}
