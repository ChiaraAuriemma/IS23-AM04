package it.polimi.it.model;

public abstract class CommonGoalCard extends Card{

    protected int id;
    private final String name;

    public CommonGoalCard(){
        this.name = "Common";
    }

    public abstract Boolean CheckGoal();
}
