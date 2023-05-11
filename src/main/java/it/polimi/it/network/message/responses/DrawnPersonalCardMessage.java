package it.polimi.it.network.message.responses;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class DrawnPersonalCardMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 2453415242475482909L;
    private PersonalGoalCard card;

    public DrawnPersonalCardMessage(PersonalGoalCard card){
        this.card = card;
    }

    public PersonalGoalCard getCard() {
        return card;
    }
}
