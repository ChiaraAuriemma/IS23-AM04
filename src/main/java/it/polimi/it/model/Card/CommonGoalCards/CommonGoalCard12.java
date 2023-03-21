package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard12 extends CommonGoalCard{

    public CommonGoalCard12(){
        super();
        this.id = 12;
    }

    public Boolean CheckGoal(Shelfie shelfie){
        String Cell1 = shelfie.getCell(0,5);
        String Cell2 = shelfie.getCell(1,4);
        String Cell3 = shelfie.getCell(2,3);
        String Cell4 = shelfie.getCell(3,2);
        String Cell5 = shelfie.getCell(4,1);
        String Cell6 = shelfie.getCell(0,4);
        String Cell7 = shelfie.getCell(1,3);
        String Cell8 = shelfie.getCell(2,2);
        String Cell9 = shelfie.getCell(3,1);
        String Cell10 = shelfie.getCell(4,0);
        String Cell11 = shelfie.getCell(0,0);
        String Cell12 = shelfie.getCell(1,1);
        String Cell13 = shelfie.getCell(3,3);
        String Cell14 = shelfie.getCell(4,5);
        String Cell15 = shelfie.getCell(0,1);
        String Cell16 = shelfie.getCell(1,2);
        String Cell17 = shelfie.getCell(3,4);
        String Cell18 = shelfie.getCell(0,2);
        String Cell19 = shelfie.getCell(1,5);
        String Cell20 = shelfie.getCell(2,4);
        String Cell21 = shelfie.getCell(4,2);
        String Cell22 = shelfie.getCell(3,5);


        if(!Cell1.equals("DEFAULT") && !Cell2.equals("DEFAULT") && !Cell3.equals("DEFAULT") && !Cell4.equals("DEFAULT") && !Cell5.equals("DEFAULT")
            && Cell19.equals("DEFAULT") && Cell20.equals("DEFAULT") && Cell13.equals("DEFAULT") && Cell21.equals("DEFAULT") ){
            return true;
        }

        if(!Cell6.equals("DEFAULT") && !Cell7.equals("DEFAULT") && !Cell8.equals("DEFAULT") && !Cell9.equals("DEFAULT") && !Cell10.equals("DEFAULT")
            && Cell1.equals("DEFAULT") && Cell2.equals("DEFAULT") && Cell3.equals("DEFAULT") && Cell4.equals("DEFAULT") && Cell5.equals("DEFAULT")){
            return true;
        }

        if(!Cell11.equals("DEFAULT") && !Cell12.equals("DEFAULT") && !Cell8.equals("DEFAULT") && !Cell13.equals("DEFAULT") && !Cell14.equals("DEFAULT")
            && Cell15.equals("DEFAULT") && Cell16.equals("DEFAULT") && Cell3.equals("DEFAULT") && Cell17.equals("DEFAULT")){
            return true;
        }

        if(!Cell15.equals("DEFAULT") && !Cell16.equals("DEFAULT") && !Cell3.equals("DEFAULT") && !Cell17.equals("DEFAULT") && !Cell14.equals("DEFAULT")
            && Cell18.equals("DEFAULT") && Cell7.equals("DEFAULT") && Cell20.equals("DEFAULT") && Cell22.equals("DEFAULT")){
            return true;
        }

        return false;

    }
}