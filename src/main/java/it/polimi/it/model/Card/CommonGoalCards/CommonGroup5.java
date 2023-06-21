package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonGroup5 extends CommonGoalCard  implements Serializable {

    private static final long serialVersionUID = -7822537206787354297L;
    private List<Integer> numColor;


    /**
     * constructor of the CommonGroup5.
     * @param id is the identification code of the CommonGoalCard, in this group the option for the id is 9
     */
    public CommonGroup5(int id){
        super(id);
        this.numColor = new ArrayList<Integer>(6);
        int i;
        for(i=0; i<6; i++) {
            this.numColor.add(i, 0);
        }
    }


    /**
     * Method that check the goal of the CommonGoalCard
     * @param shelfie is the shelfie that we need to check
     * @return true or false
     */
    public Boolean checkGoal(Shelfie shelfie){
        int i,column,row,tmp;
        for(column=0;column<5;column++){
            for(row=0; row<6; row++){
                if(shelfie.getCell(column,row).getColor().equals("PINK")){
                    tmp = numColor.get(0);
                    tmp++;
                    numColor.set(0,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("CYAN")){
                    tmp = numColor.get(1);
                    tmp++;;
                    numColor.set(1,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("YELLOW")){
                    tmp = numColor.get(2);
                    tmp++;
                    numColor.set(2,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("BLUE")){
                    tmp = numColor.get(3);
                    tmp++;
                    numColor.set(3,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("WHITE")){
                    tmp = numColor.get(4);
                    tmp++;
                    numColor.set(4,tmp);
                }
                if(shelfie.getCell(column,row).getColor().equals("GREEN")){
                    tmp = numColor.get(5);
                    tmp++;
                    numColor.set(5,tmp);
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