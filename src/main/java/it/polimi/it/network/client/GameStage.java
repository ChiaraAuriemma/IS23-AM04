package it.polimi.it.network.client;

/**
 * GameStage is used alongside the TurnStages enumeration to keep a record of the current Stage of the Game.
 * The user can make different actions according to the Stage.
 */
public class GameStage {

    private TurnStages stage;


    /**
     * Setter method for the current
     * @param stage .
     */
    public void setStage(TurnStages stage){
        this.stage = stage;
    }


    /**
     * Getter method
     * @return the current stage.
     */
    public TurnStages getStage() {
        return stage;
    }
}
