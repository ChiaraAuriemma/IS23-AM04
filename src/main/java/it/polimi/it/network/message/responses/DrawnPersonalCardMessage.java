package it.polimi.it.network.message.responses;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.network.message.Payload;

public class DrawnPersonalCardMessage extends Payload {

    private PersonalGoalCard card;

    public DrawnPersonalCardMessage(PersonalGoalCard card){
        this.card = card;
    }

    public PersonalGoalCard getCard() {
        return card;
    }
}
