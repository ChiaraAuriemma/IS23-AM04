package it.polimi.it.model;

public class CommonGoalCard8 extends CommonGoalCard {

    public CommonGoalCard8() {
        super();
        this.id = 8;
    }

    public Boolean CheckGoal(Shelfie shelfie) {
        String Cell1 = shelfie.getCell(0,0);
        String Cell2 = shelfie.getCell(0,5);
        String Cell3 = shelfie.getCell(4,5);
        String Cell4 = shelfie.getCell(4,0);

        if(!Cell1.equals("DEFAULT") &&  Cell1.equals(Cell2) && Cell2.equals(Cell3) && Cell3.equals(Cell4)){
            return true;
        }else {return false;}

    }
}