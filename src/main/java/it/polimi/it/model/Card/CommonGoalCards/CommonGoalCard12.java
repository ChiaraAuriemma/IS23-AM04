package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard12 extends CommonGoalCard{

    public CommonGoalCard12(){
        super();
        this.id = 12;
    }

    public Boolean checkGoal(Shelfie shelfie){
        String cell1 = shelfie.getCell(0,5);
        String cell2 = shelfie.getCell(1,4);
        String cell3 = shelfie.getCell(2,3);
        String cell4 = shelfie.getCell(3,2);
        String cell5 = shelfie.getCell(4,1);
        String cell6 = shelfie.getCell(0,4);
        String cell7 = shelfie.getCell(1,3);
        String cell8 = shelfie.getCell(2,2);
        String cell9 = shelfie.getCell(3,1);
        String cell10 = shelfie.getCell(4,0);
        String cell11 = shelfie.getCell(0,0);
        String cell12 = shelfie.getCell(1,1);
        String cell13 = shelfie.getCell(3,3);
        String cell14 = shelfie.getCell(4,4);
        String cell15 = shelfie.getCell(0,1);
        String cell16 = shelfie.getCell(1,2);
        String cell17 = shelfie.getCell(3,4);
        String cell18 = shelfie.getCell(4,5);
        String cell19 = shelfie.getCell(0,2);
        String cell20 = shelfie.getCell(1,5);
        String cell21 = shelfie.getCell(2,4);
        String cell22 = shelfie.getCell(4,2);
        String cell23 = shelfie.getCell(3,5);


        if(!cell1.equals("DEFAULT") && !cell2.equals("DEFAULT") && !cell3.equals("DEFAULT") && !cell4.equals("DEFAULT") && !cell5.equals("DEFAULT")
            && cell20.equals("DEFAULT") && cell21.equals("DEFAULT") && cell13.equals("DEFAULT") && cell22.equals("DEFAULT") ){
            return true;
        }

        if(!cell6.equals("DEFAULT") && !cell7.equals("DEFAULT") && !cell8.equals("DEFAULT") && !cell9.equals("DEFAULT") && !cell10.equals("DEFAULT")
            && cell1.equals("DEFAULT") && cell2.equals("DEFAULT") && cell3.equals("DEFAULT") && cell4.equals("DEFAULT") && cell5.equals("DEFAULT")){
            return true;
        }

        if(!cell11.equals("DEFAULT") && !cell12.equals("DEFAULT") && !cell8.equals("DEFAULT") && !cell13.equals("DEFAULT") && !cell14.equals("DEFAULT")
            && cell15.equals("DEFAULT") && cell16.equals("DEFAULT") && cell3.equals("DEFAULT") && cell17.equals("DEFAULT") && cell18.equals("DEFAULT")){
            return true;
        }

        if(!cell15.equals("DEFAULT") && !cell16.equals("DEFAULT") && !cell3.equals("DEFAULT") && !cell17.equals("DEFAULT") && !cell18.equals("DEFAULT")
            && cell19.equals("DEFAULT") && cell7.equals("DEFAULT") && cell21.equals("DEFAULT") && cell23.equals("DEFAULT")){
            return true;
        }

        return false;

    }
}