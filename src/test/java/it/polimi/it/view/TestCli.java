package it.polimi.it.view;

import it.polimi.it.model.Card.CommonGoalCards.CommonDeck;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;
import it.polimi.it.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestCli {
    private View testView;
    private final String border = "║";
    private final String noPlayer = "           ";


    @Before
    public void View(){
        this.testView = new View();
    }

    @Test
    public void basic(){
        System.out.println(testView.firstLine);
        System.out.println(border + "   " + "JackB1233mmm" + "   " + "JackB1233mmm" + "   " + "JackB1233mmm" + "   " + "JackB1233mmm" + "   " + border);
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println ("\u001B[43m" + "  vmkvjgck                  " +
                "\u001B[0m" + " altra roba");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        System.out.println ("\u001B[43m  " + " "+"\u001B[43m" + " " + "\u001B[44m" + "  " + "\u001B[0m" + "  "+ "\u001B[0m" + "  "+ "\u001B[45m" + "  " + "\u001B[0m" + "  \n");
        System.out.println ("\u001B[43m" + "  " + "\u001B[44m" + "  " + "\u001B[45m" + "  " + "\u001B[0m" + "  ");
    }


    @Test
    public void advanced(){
        CommonDeck commonDeck = new CommonDeck();
        commonDeck.createCards(1,10);
        CommonGoalCard card1 = commonDeck.getCommonCard1();
        CommonGoalCard card2 = commonDeck.getCommonCard2();
        //PersonalGoalCard pgc = new PersonalGoalCard(4);


        testView.setGameID(12);
        testView.setCommon1View(card1);
        testView.setCommon2View(card2);
        //testView.setPlayersPersonalCardView(pgc);

        testView.fakePersonal();

        ArrayList<User> order = new ArrayList<>();
        User u = new User("JackB");
        order.add(u);
        User u2 = new User("Alby");
        order.add(u2);
        User u3 = new User("Chiara");
        order.add(u3);
        User u4 = new User("Fra");
        order.add(u4);

        ArrayList<String> a = new ArrayList<>();
        for (User uefwjrej: order){
            a.add(testView.nickPadder(uefwjrej.getNickname()));
        }
        testView.setOrderView(a);

        testView.setPlayersPointsView(a.get(0), 3);
        testView.setPlayersPointsView(a.get(1), 39);
        testView.setPlayersPointsView(a.get(2), 6);
        testView.setPlayersPointsView(a.get(3), 23);

        Tile[][] matrix = new Tile[6][5];
        TilesBag bag = new TilesBag();
        for(int column=0; column<5; column++){
            for (int row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }
        testView.setPlayersShelfiesView(a.get(0), matrix);
        for(int column=0; column<5; column++){
            for (int row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }
        testView.setPlayersShelfiesView(a.get(1), matrix);

        for(int column=0; column<5; column++){
            for (int row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }
        testView.setPlayersShelfiesView(a.get(2), matrix);

        for(int column=0; column<5; column++){
            for (int row=0; row<6; row++){
                matrix[row][column] = bag.randomTiles(row,column);
            }
        }
        testView.setPlayersShelfiesView(a.get(3), matrix);



        TilesBag bag2 = new TilesBag();

        Tile[][] board = new  Tile[9][9];
        for(int column=0; column<9; column++){
            for (int row=0; row<9; row++){
                board[row][column] = bag2.randomTiles(row,column);
            }
        }

        testView.setBoardView(board);
                testView.setChat();
        testView.update();
    }
}
