package it.polimi.it.model.Card;

import it.polimi.it.model.Card.CommonGoalCards.CommonDeck;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;
import it.polimi.it.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class CommonGroup2Test {

    private CommonGoalCard card1;
    private CommonGoalCard card2;
    private CommonDeck commonDeck;

    private Shelfie shelfie;
    private TilesBag bag;
    private User u = new User(" ");
    @Before
    public void card(){
        this.commonDeck = new CommonDeck();
        commonDeck.createCards(7,6);
        this.card1 = commonDeck.getCommonCard1();
        this.card2 = commonDeck.getCommonCard2();
        this.shelfie = new Shelfie(u);
        this.bag = new TilesBag();
    }

    @Test
    public void checkGoalTest6(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[0][1] = new Tile(0, 1,PossibleColors.CYAN);
        matrix[0][2] = new Tile(0, 2,PossibleColors.YELLOW);
        matrix[0][3] = new Tile(0, 3,PossibleColors.BLUE);
        matrix[0][4] = new Tile(0, 4,PossibleColors.WHITE);

        matrix[1][0] = new Tile(1, 0, PossibleColors.YELLOW);
        matrix[1][1] = new Tile(1, 1,PossibleColors.BLUE);
        matrix[1][2] = new Tile(1, 2,PossibleColors.PINK);
        matrix[1][3] = new Tile(1, 3,PossibleColors.WHITE);
        matrix[1][4] = new Tile(1, 4,PossibleColors.GREEN);

        matrix[2][0] = new Tile(2,0,PossibleColors.PINK);
        matrix[2][1] = new Tile(2,1,PossibleColors.PINK);

        matrix[3][0] = new Tile(3,0,PossibleColors.CYAN);
        matrix[3][1] = new Tile(3,1,PossibleColors.CYAN);

        matrix[4][0] = new Tile(4,0,PossibleColors.GREEN);
        matrix[4][1] = new Tile(4,1,PossibleColors.GREEN);

        matrix[5][0] = new Tile(5,0,PossibleColors.YELLOW);
        matrix[5][1] = new Tile(5,1,PossibleColors.YELLOW);

        shelfie.setShelf(matrix);

        if(!card2.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest7(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[0][1] = new Tile(0, 1,PossibleColors.PINK);

        matrix[1][0] = new Tile(1, 0, PossibleColors.YELLOW);
        matrix[1][1] = new Tile(1, 1,PossibleColors.YELLOW);

        matrix[2][0] = new Tile(2,0,PossibleColors.PINK);
        matrix[2][1] = new Tile(2,1,PossibleColors.PINK);

        matrix[3][0] = new Tile(3,0,PossibleColors.CYAN);
        matrix[3][1] = new Tile(3,1,PossibleColors.CYAN);

        matrix[4][0] = new Tile(4,0,PossibleColors.GREEN);
        matrix[4][1] = new Tile(4,1,PossibleColors.GREEN);

        matrix[5][0] = new Tile(5,0,PossibleColors.YELLOW);
        matrix[5][1] = new Tile(5,1,PossibleColors.YELLOW);

        shelfie.setShelf(matrix);

        if(card2.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest8(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[0][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[0][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[0][3] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[0][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[1][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[1][2] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][3] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[1][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[2][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[2][1] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[2][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[2][3] = new Tile(0, 1,PossibleColors.PINK);
        matrix[2][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[3][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[3][1] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[3][2] = new Tile(0, 0, PossibleColors.BLUE);
        matrix[3][3] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[3][4] = new Tile(0, 1,PossibleColors.PINK);

        matrix[4][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[4][2] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][3] = new Tile(0, 1,PossibleColors.GREEN);
        matrix[4][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[5][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[5][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[5][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[5][3] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[5][4] = new Tile(0, 1,PossibleColors.PINK);

        shelfie.setShelf(matrix);

        if(card2.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest9(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[0][1] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[0][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[0][3] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[0][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[1][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[1][2] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][3] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[1][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[2][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[2][1] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[2][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[2][3] = new Tile(0, 1,PossibleColors.PINK);
        matrix[2][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[3][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[3][1] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[3][2] = new Tile(0, 0, PossibleColors.BLUE);
        matrix[3][3] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[3][4] = new Tile(0, 1,PossibleColors.PINK);

        matrix[4][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[4][2] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][3] = new Tile(0, 1,PossibleColors.GREEN);
        matrix[4][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[5][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[5][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[5][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[5][3] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[5][4] = new Tile(0, 1,PossibleColors.PINK);

        shelfie.setShelf(matrix);

        if(card2.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest10(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[0][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[0][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[0][3] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[0][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[1][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[1][2] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][3] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[1][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[2][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[2][1] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[2][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[2][3] = new Tile(0, 1,PossibleColors.PINK);
        matrix[2][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[3][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[3][1] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[3][2] = new Tile(0, 0, PossibleColors.BLUE);
        matrix[3][3] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[3][4] = new Tile(0, 1,PossibleColors.PINK);

        matrix[4][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[4][2] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][3] = new Tile(0, 1,PossibleColors.GREEN);
        matrix[4][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[5][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[5][1] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[5][2] = new Tile(0, 0, PossibleColors.BLUE);
        matrix[5][3] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[5][4] = new Tile(0, 1,PossibleColors.CYAN);

        shelfie.setShelf(matrix);

        if(!card2.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest14(){ //testing card 7
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[0][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[0][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[0][3] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[0][4] = new Tile(0, 1,PossibleColors.BLUE);

        matrix[1][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[1][2] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][3] = new Tile(0, 1,PossibleColors.PINK);
        matrix[1][4] = new Tile(0, 1,PossibleColors.PINK);

        matrix[2][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[2][1] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[2][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[2][3] = new Tile(0, 1,PossibleColors.PINK);
        matrix[2][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[3][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[3][1] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[3][2] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[3][3] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[3][4] = new Tile(0, 1,PossibleColors.PINK);

        matrix[4][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][1] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[4][2] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][3] = new Tile(0, 1,PossibleColors.GREEN);
        matrix[4][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[5][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[5][1] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[5][2] = new Tile(0, 0, PossibleColors.BLUE);
        matrix[5][3] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[5][4] = new Tile(0, 1,PossibleColors.CYAN);

        shelfie.setShelf(matrix);

        if(!card1.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest15(){ //testing card 7
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.GREEN);
        matrix[0][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[0][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[0][3] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[0][4] = new Tile(0, 1,PossibleColors.BLUE);

        matrix[1][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][1] = new Tile(0, 1,PossibleColors.PINK);
        matrix[1][2] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[1][3] = new Tile(0, 1,PossibleColors.PINK);
        matrix[1][4] = new Tile(0, 1,PossibleColors.PINK);

        matrix[2][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[2][1] = new Tile(0, 1,PossibleColors.BLUE);
        matrix[2][2] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[2][3] = new Tile(0, 1,PossibleColors.PINK);
        matrix[2][4] = new Tile(0, 1,PossibleColors.YELLOW);

        matrix[3][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[3][1] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[3][2] = new Tile(0, 0, PossibleColors.CYAN);
        matrix[3][3] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[3][4] = new Tile(0, 1,PossibleColors.PINK);

        matrix[4][0] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][1] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[4][2] = new Tile(0, 0, PossibleColors.WHITE);
        matrix[4][3] = new Tile(0, 1,PossibleColors.GREEN);
        matrix[4][4] = new Tile(0, 1,PossibleColors.GREEN);

        matrix[5][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[5][1] = new Tile(0, 1,PossibleColors.WHITE);
        matrix[5][2] = new Tile(0, 0, PossibleColors.BLUE);
        matrix[5][3] = new Tile(0, 1,PossibleColors.YELLOW);
        matrix[5][4] = new Tile(0, 1,PossibleColors.CYAN);

        shelfie.setShelf(matrix);

        if(card1.checkGoal(shelfie))
            fail("Test failed");
    }

   @Test
    public void checkGoalTest21(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];

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

        if(card2.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest22(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.DEFAULT);
        matrix[1][0] = new Tile(1,0,PossibleColors.DEFAULT);
        matrix[2][0] = new Tile(2,0,PossibleColors.DEFAULT);
        matrix[3][0] = new Tile(3, 0,PossibleColors.DEFAULT);
        matrix[4][0] = new Tile(4,0,PossibleColors.DEFAULT);
        matrix[5][0] = new Tile(5,0,PossibleColors.DEFAULT);

        matrix[0][1] = new Tile(0, 1, PossibleColors.PINK);
        matrix[1][1] = new Tile(1,1,PossibleColors.PINK);
        matrix[2][1] = new Tile(2,1,PossibleColors.PINK);
        matrix[3][1] = new Tile(3, 1,PossibleColors.PINK);
        matrix[4][1] = new Tile(4,1,PossibleColors.PINK);
        matrix[5][1] = new Tile(5,1,PossibleColors.PINK);

        matrix[0][2] = new Tile(0, 2, PossibleColors.CYAN);
        matrix[1][2] = new Tile(1,2,PossibleColors.CYAN);
        matrix[2][2] = new Tile(2,2,PossibleColors.CYAN);
        matrix[3][2] = new Tile(3, 2,PossibleColors.CYAN);
        matrix[4][2] = new Tile(4,2,PossibleColors.CYAN);
        matrix[5][2] = new Tile(5,2,PossibleColors.CYAN);

        matrix[0][3] = new Tile(0, 3, PossibleColors.BLUE);
        matrix[1][3] = new Tile(1,3,PossibleColors.BLUE);
        matrix[2][3] = new Tile(2,3,PossibleColors.BLUE);
        matrix[3][3] = new Tile(3, 3,PossibleColors.BLUE);
        matrix[4][3] = new Tile(4,3,PossibleColors.BLUE);
        matrix[5][3] = new Tile(5,3,PossibleColors.BLUE);

        matrix[0][4] = new Tile(0, 4, PossibleColors.YELLOW);
        matrix[1][4] = new Tile(1,4,PossibleColors.YELLOW);
        matrix[2][4] = new Tile(2,4,PossibleColors.YELLOW);
        matrix[3][4] = new Tile(3, 4,PossibleColors.YELLOW);
        matrix[4][4] = new Tile(4,4,PossibleColors.YELLOW);
        matrix[5][4] = new Tile(5,4,PossibleColors.YELLOW);

        shelfie.setShelf(matrix);

        if(card2.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest23(){ //testing card 7
        int column, row;
        Tile[][] matrix = new Tile[6][5];

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

        if(card1.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest24(){ //testing card 7
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.DEFAULT);
        matrix[1][0] = new Tile(1,0,PossibleColors.CYAN);
        matrix[2][0] = new Tile(2,0,PossibleColors.DEFAULT);
        matrix[3][0] = new Tile(3, 0,PossibleColors.DEFAULT);
        matrix[4][0] = new Tile(4,0,PossibleColors.DEFAULT);
        matrix[5][0] = new Tile(5,0,PossibleColors.DEFAULT);

        matrix[0][1] = new Tile(0, 1, PossibleColors.DEFAULT);
        matrix[1][1] = new Tile(1,1,PossibleColors.CYAN);
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

        matrix[0][3] = new Tile(0, 3, PossibleColors.PINK);
        matrix[1][3] = new Tile(1,3,PossibleColors.DEFAULT);
        matrix[2][3] = new Tile(2,3,PossibleColors.BLUE);
        matrix[3][3] = new Tile(3, 3,PossibleColors.DEFAULT);
        matrix[4][3] = new Tile(4,3,PossibleColors.DEFAULT);
        matrix[5][3] = new Tile(5,3,PossibleColors.DEFAULT);

        matrix[0][4] = new Tile(0, 4, PossibleColors.YELLOW);
        matrix[1][4] = new Tile(1,4,PossibleColors.DEFAULT);
        matrix[2][4] = new Tile(2,4,PossibleColors.DEFAULT);
        matrix[3][4] = new Tile(3, 4,PossibleColors.DEFAULT);
        matrix[4][4] = new Tile(4,4,PossibleColors.WHITE);
        matrix[5][4] = new Tile(5,4,PossibleColors.DEFAULT);

        shelfie.setShelf(matrix);

        if(card1.checkGoal(shelfie))
            fail("Test failed");
    }

    @Test
    public void checkGoalTest25(){ //testing card 6
        int column, row;
        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.YELLOW);
        matrix[1][0] = new Tile(1,0,PossibleColors.BLUE);
        matrix[2][0] = new Tile(2,0,PossibleColors.DEFAULT);
        matrix[3][0] = new Tile(3, 0,PossibleColors.DEFAULT);
        matrix[4][0] = new Tile(4,0,PossibleColors.DEFAULT);
        matrix[5][0] = new Tile(5,0,PossibleColors.DEFAULT);

        matrix[0][1] = new Tile(0, 1, PossibleColors.PINK);
        matrix[1][1] = new Tile(1,1,PossibleColors.YELLOW);
        matrix[2][1] = new Tile(2,1,PossibleColors.DEFAULT);
        matrix[3][1] = new Tile(3, 1,PossibleColors.DEFAULT);
        matrix[4][1] = new Tile(4,1,PossibleColors.DEFAULT);
        matrix[5][1] = new Tile(5,1,PossibleColors.DEFAULT);

        matrix[0][2] = new Tile(0, 2, PossibleColors.CYAN);
        matrix[1][2] = new Tile(1,2,PossibleColors.WHITE);
        matrix[2][2] = new Tile(2,2,PossibleColors.DEFAULT);
        matrix[3][2] = new Tile(3, 2,PossibleColors.DEFAULT);
        matrix[4][2] = new Tile(4,2,PossibleColors.DEFAULT);
        matrix[5][2] = new Tile(5,2,PossibleColors.DEFAULT);

        matrix[0][3] = new Tile(0, 3, PossibleColors.WHITE);
        matrix[1][3] = new Tile(1,3,PossibleColors.CYAN);
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

        if(card2.checkGoal(shelfie))
            fail("Test failed");
    }
}
