package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard8 extends CommonGoalCard {

    public CommonGoalCard8() {
        super();
        this.id = 8;
    }

    public Boolean checkGoal(Shelfie shelfie) {
        String cell1 = shelfie.getCell(0,0);
        String cell2 = shelfie.getCell(0,5);
        String cell3 = shelfie.getCell(4,5);
        String cell4 = shelfie.getCell(4,0);

        if(!cell1.equals("DEFAULT") &&  cell1.equals(cell2) && cell2.equals(cell3) && cell3.equals(cell4)){
            return true;
        }else {return false;}

    }
}