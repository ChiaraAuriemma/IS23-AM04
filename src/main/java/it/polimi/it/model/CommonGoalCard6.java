package it.polimi.it.model;

public class CommonGoalCard6 extends CommonGoalCard{

    public CommonGoalCard6(){
        super();
        this.id = 6;
    }

    public Boolean CheckGoal(Shelfie shelfie){
        int i,j;
        int stop;
        int NumRightLine = 0;

        for(j=0; j<6 && NumRightLine<2; j++) {
            stop = 0;
            for(i=0; i<4 && stop == 0; i++){
                if(shelfie.getCell(i,j).equals(shelfie.getCell(i+1,j)) || shelfie.getCell(i,j).equals("DEFAULT"))
                    stop=1;
            }
            if(stop==0)
                NumRightLine++;
        }

        if(NumRightLine == 2)
            return true;
        else return false;

    }
}
