package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

import java.util.ArrayList;
import java.util.List;

public class CommonGroup5 extends CommonGoalCard{

    private List<Integer> numColor;

    public CommonGroup5(int id){
        super(id);
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
                if(shelfie.getCell(column,row).getColor().equals("PINK")){
                    tmp = numColor.get(0);
                    tmp++;
                    numColor.remove(0);
                    numColor.add(0,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("CYAN")){
                    tmp = numColor.get(1);
                    tmp++;
                    numColor.remove(1);
                    numColor.add(1,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("YELLOW")){
                    tmp = numColor.get(2);
                    tmp++;
                    numColor.remove(2);
                    numColor.add(2,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("BLUE")){
                    tmp = numColor.get(3);
                    tmp++;
                    numColor.remove(3);
                    numColor.add(3,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("WHITE")){
                    tmp = numColor.get(4);
                    tmp++;
                    numColor.remove(4);
                    numColor.add(4,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("GREEN")){
                    tmp = numColor.get(5);
                    tmp++;
                    numColor.remove(5);
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