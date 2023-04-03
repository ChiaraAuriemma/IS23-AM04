package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public abstract class CommonGoalCard {

    protected int id;

    public CommonGoalCard(int id){
        this.id = id;
    }

    public abstract Boolean checkGoal(Shelfie shelfie);
}
