package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCard9 extends CommonGoalCard{

    private List<Integer> numColor;

    public CommonGoalCard9(){
        super();
        this.id = 9;
        this.numColor = new ArrayList<Integer>(6);
        int i;
        for(i=0; i<6; i++) {
            this.numColor.add(i, 0);
        }
    }

    public Boolean checkGoal(Shelfie shelfie){
        int i,column,row,tmp;
        for(column=0;column<5;column++){
            for(row=0; row<6; row++){
                if(shelfie.getCell(column,row).equals("PINK")){
                    tmp = numColor.get(0);
                    tmp++;
                    numColor.add(0,tmp);
                }
                if(shelfie.getCell(column,row).equals("CYAN")){
                    tmp = numColor.get(1);
                    tmp++;
                    numColor.add(1,tmp);
                }
                if(shelfie.getCell(column,row).equals("YELLOW")){
                    tmp = numColor.get(2);
                    tmp++;
                    numColor.add(2,tmp);
                }
                if(shelfie.getCell(column,row).equals("BLUE")){
                    tmp = numColor.get(3);
                    tmp++;
                    numColor.add(3,tmp);
                }
                if(shelfie.getCell(column,row).equals("WHITE")){
                    tmp = numColor.get(4);
                    tmp++;
                    numColor.add(4,tmp);
                }
                if(shelfie.getCell(column,row).equals("GREEN")){
                    tmp = numColor.get(5);
                    tmp++;
                    numColor.add(5,tmp);
                }
                for(i=0;i<6;i++){
                    if(numColor.get(i) == 8)
                        return true;
                }

            }
        }
        return false;
    }
}