package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard8 extends CommonGoalCard {

    public CommonGoalCard8() {
        super();
        this.id = 8;
    }

    public Boolean checkGoal(Shelfie shelfie) {
        String cell1 = shelfie.getCell(0,0).getColor();
        String cell2 = shelfie.getCell(0,5).getColor();
        String cell3 = shelfie.getCell(4,5).getColor();
        String cell4 = shelfie.getCell(4,0).getColor();

        if(!cell1.equals("DEFAULT") &&  cell1.equals(cell2) && cell2.equals(cell3) && cell3.equals(cell4)){
            return true;
        }else {return false;}

    }
}