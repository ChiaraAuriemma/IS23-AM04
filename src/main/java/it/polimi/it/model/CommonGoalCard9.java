package it.polimi.it.model;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCard9 extends CommonGoalCard{

    private List<Integer> NumColor;

    public CommonGoalCard9(){
        super();
        this.id = 9;
        this.NumColor = new ArrayList<Integer>(6);
        int i;
        for(i=0; i<6; i++) {
            this.NumColor.add(i, 0);
        }
    }

    public Boolean CheckGoal(Shelfie shelfie){
        int i,k,j,tmp;
        for(k=0;k<5;k++){
            for(j=0; j<6; j++){
                if(shelfie.getCell(k,j).equals("PINK")){
                    tmp = NumColor.get(0);
                    tmp++;
                    NumColor.add(0,tmp);
                }
                if(shelfie.getCell(k,j).equals("CYAN")){
                    tmp = NumColor.get(1);
                    tmp++;
                    NumColor.add(1,tmp);
                }
                if(shelfie.getCell(k,j).equals("YELLOW")){
                    tmp = NumColor.get(2);
                    tmp++;
                    NumColor.add(2,tmp);
                }
                if(shelfie.getCell(k,j).equals("BLUE")){
                    tmp = NumColor.get(3);
                    tmp++;
                    NumColor.add(3,tmp);
                }
                if(shelfie.getCell(k,j).equals("WHITE")){
                    tmp = NumColor.get(4);
                    tmp++;
                    NumColor.add(4,tmp);
                }
                if(shelfie.getCell(k,j).equals("GREEN")){
                    tmp = NumColor.get(5);
                    tmp++;
                    NumColor.add(5,tmp);
                }
                for(i=0;i<6;i++){
                    if(NumColor.get(i) == 8)
                        return true;
                }

            }
        }
        return false;
    }
}