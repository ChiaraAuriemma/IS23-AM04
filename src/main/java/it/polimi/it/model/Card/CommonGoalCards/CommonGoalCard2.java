package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public class CommonGoalCard2 extends CommonGoalCard{

    public CommonGoalCard2(){
        super();
        this.id = 2;
    }

    public Boolean CheckGoal(Shelfie shelfie){
        int i,j;
        int stop;
        int NumRightCol = 0;

        for(i=0; i<5 && NumRightCol<2; i++) {
            stop = 0;
            for(j=0; j<5 && stop == 0; j++){
                if(shelfie.getCell(i,j).equals(shelfie.getCell(i,j+1)) || shelfie.getCell(i,j).equals("DEFAULT"))
                    stop=1;
            }
            if(stop==0)
                NumRightCol++;
        }

        if(NumRightCol == 2)
            return true;
        else return false;

    }
}
