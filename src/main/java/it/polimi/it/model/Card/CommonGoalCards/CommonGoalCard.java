package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;
import java.io.Serializable;

/**
 * Abstract class for the common goal cards
 */
public abstract class CommonGoalCard implements Serializable {

    private static final long serialVersionUID = -3433170783937394388L;
    protected int id;


    /**
     * constructor of the CommonGoalCard
     * @param id is the identification code of the CommonGoal
     */
    public CommonGoalCard(int id){
        this.id = id;
    }


    /**
     * abstract method that will be implemented in the CommonGroups. Check the goals in the shelfie
     * @param shelfie is the shelfie that we need to check
     * @return true or false
     */
    public abstract Boolean checkGoal(Shelfie shelfie);
    
    
    /**
    *Getter method for the id of the card
    */
    public int getID(){
        return id;
    }
}
