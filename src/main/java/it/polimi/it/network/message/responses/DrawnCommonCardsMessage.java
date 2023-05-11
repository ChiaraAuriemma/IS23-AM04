package it.polimi.it.network.message.responses;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.List;

public class DrawnCommonCardsMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 8895460901257459083L;
    private CommonGoalCard card1;
    private CommonGoalCard card2;
    private List<Integer> commonToken1;
    private List<Integer> commonToken2;

    public DrawnCommonCardsMessage(CommonGoalCard card1, CommonGoalCard card2, List<Integer> commonToken1, List<Integer> commonToken2){
        this.card1 = card1;
        this.card2 = card2;
        this.commonToken1 = commonToken1;
        this.commonToken2 = commonToken2;
    }

    public CommonGoalCard getCard1() {
        return card1;
    }

    public CommonGoalCard getCard2() {
        return card2;
    }

    public List<Integer> getCommonToken1() {
        return commonToken1;
    }

    public List<Integer> getCommonToken2() {
        return commonToken2;
    }
}
