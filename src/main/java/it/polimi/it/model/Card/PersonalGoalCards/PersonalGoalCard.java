package it.polimi.it.model.Card.PersonalGoalCards;

import it.polimi.it.model.Card.Card;
import it.polimi.it.model.Shelfie;

import java.util.ArrayList;
import java.util.List;

public abstract class PersonalGoalCard extends Card {
    protected int id;
    private final String name;
    private int numCompletedTasks;
    protected List<Integer> pinkPos;
    protected List<Integer> cyanPos;
    protected List<Integer> yellowPos;
    protected List<Integer> bluePos;
    protected List<Integer> whitePos;
    protected List<Integer> greenPos;
    private List<Integer> checkColor;


    public PersonalGoalCard(){
        this.name = "Personal";
        this.numCompletedTasks = 0;
        this.checkColor = new ArrayList<Integer>(6);
        int i;
        for(i=0; i<6; i++){
            this.checkColor.add(i,0);
        }
        this.pinkPos = new ArrayList<Integer>(2);
        this.cyanPos = new ArrayList<Integer>(2);
        this.yellowPos = new ArrayList<Integer>(2);
        this.bluePos = new ArrayList<Integer>(2);
        this.whitePos = new ArrayList<Integer>(2);
        this.greenPos = new ArrayList<Integer>(2);
    }


    public int checkScore(Shelfie shelfie) {
        if(checkColor.get(0) == 0 && shelfie.getCell(pinkPos.get(0), pinkPos.get(1)).equals("PINK")){
            numCompletedTasks++;
            checkColor.add(0,1);
        }
        if(checkColor.get(1) == 0 && shelfie.getCell(cyanPos.get(0), cyanPos.get(1)).equals("CYAN")){
            numCompletedTasks++;
            checkColor.add(1,1);
        }
        if(checkColor.get(2)== 0 && shelfie.getCell(yellowPos.get(0), yellowPos.get(1)).equals("YELLOW")){
            numCompletedTasks++;
            checkColor.add(2,1);
        }
        if(checkColor.get(3) == 0 && shelfie.getCell(bluePos.get(0), bluePos.get(1)).equals("BLUE")){
            numCompletedTasks++;
            checkColor.add(3,1);
        }
        if(checkColor.get(4) == 0 && shelfie.getCell(whitePos.get(0), whitePos.get(1)).equals("WHITE")){
            numCompletedTasks++;
            checkColor.add(4,1);
        }
        if(checkColor.get(5) == 0 && shelfie.getCell(greenPos.get(0), greenPos.get(1)).equals("GREEN")){
            numCompletedTasks++;
            checkColor.add(5,1);
        }

        return numCompletedTasks;
    }
}
