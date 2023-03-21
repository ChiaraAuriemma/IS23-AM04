package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard2 extends CommonGoalCard{

    public CommonGoalCard2(){
        super();
        this.id = 2;
    }

    public Boolean checkGoal(Shelfie shelfie){
        int column,row;
        int stop;
        int numRightCol = 0;

        for(column=0; column<5 && numRightCol<2; column++) {
            stop = 0;
            for(row=0; row<5 && stop == 0; row++){
                if(shelfie.getCell(column,row).equals(shelfie.getCell(column,row+1)) || shelfie.getCell(column,row).equals("DEFAULT"))
                    stop=1;
            }
            if(stop==0)
                numRightCol++;
        }

        if(numRightCol == 2)
            return true;
        else return false;

    }
}
