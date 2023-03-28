package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard6 extends CommonGoalCard{

    public CommonGoalCard6(){
        super();
        this.id = 6;
    }

    public Boolean checkGoal(Shelfie shelfie){
        int column,row;
        int stop;
        int numRightLine = 0;

        for(row=0; row<6 && numRightLine<2; row++) {
            stop = 0;
            for(column=0; column<4 && stop == 0; column++){
                if(shelfie.getCell(column,row).getColor().equals(shelfie.getCell(column+1,row).getColor()) || shelfie.getCell(column,row).getColor().equals("DEFAULT"))
                    stop=1;
            }
            if(stop==0)
                numRightLine++;
        }

        if(numRightLine == 2)
            return true;
        else return false;

    }
}
