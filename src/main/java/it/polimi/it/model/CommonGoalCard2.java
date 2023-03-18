package it.polimi.it.model;

public class CommonGoalCard2 extends CommonGoalCard{

    private int NumRightCol;
    public CommonGoalCard2(){
        super();
        this.id = 2;
    }

    public Boolean CheckGoal(Shelfie shelfie){
        int i,j;
        int stop=0;
        String ColRef1 = shelfie.getCell(0,0);
        String ColRef2 = shelfie.getCell(1,0);
        String ColRef3 = shelfie.getCell(2,0);
        String ColRef4 = shelfie.getCell(3,0);
        String ColRef5 = shelfie.getCell(4,0);


        for(i=0; i<5; i++) {
            for(j=1; j<6; j++){

            }
        }

    }
}
