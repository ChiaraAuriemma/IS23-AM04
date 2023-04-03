package it.polimi.it.model.Card.CommonGoalCards;

public class CommonDeck {

    private  CommonGoalCard commonCard1;
    private CommonGoalCard commonCard2;


    public void createCards(int id1, int id2){
        if(id1 == 1){
            commonCard1 = new CommonGroup1(id1);
        }
        if(id2 == 1){
            commonCard2 = new CommonGroup1(id2);
        }
        if(id1 == 2 || id1 == 5 || id1 == 6 || id1 == 7){
            commonCard1 = new CommonGroup2(id1);
        }
        if(id2 == 2 || id2 == 5 || id2 == 6 || id2 == 7){
            commonCard2 = new CommonGroup2(id1);
        }
        if(id1 == 3 || id1 == 4){
            commonCard1 = new CommonGroup3(id1);
        }
        if(id2 == 3 || id2 == 4){
            commonCard2 = new CommonGroup3(id2);
        }
        if(id1 == 8 || id1 == 11){
            commonCard1 = new CommonGroup4(id1);
        }
        if(id2 == 8 || id2== 11){
            commonCard2 = new CommonGroup4(id2);
        }
        if(id1 == 9){
            commonCard1 = new CommonGroup5(id1);
        }
        if(id2 == 9){
            commonCard2 = new CommonGroup5(id2);
        }
        if(id1 == 10){
            commonCard1 = new CommonGroup6(id1);
        }
        if(id2 == 10){
            commonCard2 = new CommonGroup6(id2);
        }
        if(id1 == 12){
            commonCard1 = new CommonGroup7(id1);
        }
        if(id2 == 12){
            commonCard2 = new CommonGroup7(id2);
        }
    }

    public CommonGoalCard getCommonCard1(){
        return commonCard1;
    }

    public CommonGoalCard getCommonCard2(){
        return commonCard2;
    }
}