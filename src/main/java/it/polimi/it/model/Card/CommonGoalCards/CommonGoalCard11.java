package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard11 extends CommonGoalCard{

    public CommonGoalCard11(){
        super();
        this.id = 11;
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


        if(!cell1.equals("DEFAULT") && cell1.equals(cell2) && cell2.equals(cell3) && cell3.equals(cell4) && cell4.equals(cell5)){
            return true;
        }

        if(!cell6.equals("DEFAULT") && cell6.equals(cell7) && cell7.equals(cell8) && cell8.equals(cell9) && cell9.equals(cell10)){
            return true;
        }

        if(!cell11.equals("DEFAULT") && cell11.equals(cell12) && cell12.equals(cell8) && cell8.equals(cell13) && cell13.equals(cell14)){
            return true;
        }

        if(!cell15.equals("DEFAULT") && cell15.equals(cell16) && cell16.equals(cell3) && cell3.equals(cell17) && cell17.equals(cell18)){
            return true;
        }

        return false;

    }
}

