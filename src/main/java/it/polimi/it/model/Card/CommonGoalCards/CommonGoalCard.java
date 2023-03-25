package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;

public abstract class CommonGoalCard {

    protected int id;
    private final String name;

    public CommonGoalCard(){
        this.name = "Common";
    }

    public abstract Boolean checkGoal(Shelfie shelfie);
}
