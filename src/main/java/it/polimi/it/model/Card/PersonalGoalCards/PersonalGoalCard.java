package it.polimi.it.model.Card.PersonalGoalCards;

import it.polimi.it.model.Card.Card;
import it.polimi.it.model.Shelfie;

import java.util.ArrayList;
import java.util.List;

public abstract class PersonalGoalCard extends Card {
    protected int id;
    private final String name;
    private int NumCompletedTasks;
    protected List<Integer> Pinkpos;
    protected List<Integer> Cyanpos;
    protected List<Integer> Yellowpos;
    protected List<Integer> Bluepos;
    protected List<Integer> Whitepos;
    protected List<Integer> Greenpos;
    private List<Integer> CheckColor;


    public PersonalGoalCard(){
        this.name = "Personal";
        this.NumCompletedTasks = 0;
        this.CheckColor = new ArrayList<Integer>(6);
        int i;
        for(i=0; i<6; i++){
            this.CheckColor.add(i,0);
        }
        this.Pinkpos = new ArrayList<Integer>(2);
        this.Cyanpos = new ArrayList<Integer>(2);
        this.Yellowpos = new ArrayList<Integer>(2);
        this.Bluepos = new ArrayList<Integer>(2);
        this.Whitepos = new ArrayList<Integer>(2);
        this.Greenpos = new ArrayList<Integer>(2);
    }

    //Method to check how many personal points I have made
    public int CheckScore(Shelfie shelfie) {
        if(CheckColor.get(0) == 0 && shelfie.getCell(Pinkpos.get(0), Pinkpos.get(1)).equals("PINK")){
            NumCompletedTasks++;
            CheckColor.add(0,1);
        }
        if(CheckColor.get(1) == 0 && shelfie.getCell(Cyanpos.get(0), Cyanpos.get(1)).equals("CYAN")){
            NumCompletedTasks++;
            CheckColor.add(1,1);
        }
        if(CheckColor.get(2)== 0 && shelfie.getCell(Yellowpos.get(0), Yellowpos.get(1)).equals("YELLOW")){
            NumCompletedTasks++;
            CheckColor.add(2,1);
        }
        if(CheckColor.get(3) == 0 && shelfie.getCell(Bluepos.get(0), Bluepos.get(1)).equals("BLUE")){
            NumCompletedTasks++;
            CheckColor.add(3,1);
        }
        if(CheckColor.get(4) == 0 && shelfie.getCell(Whitepos.get(0), Whitepos.get(1)).equals("WHITE")){
            NumCompletedTasks++;
            CheckColor.add(4,1);
        }
        if(CheckColor.get(5) == 0 && shelfie.getCell(Greenpos.get(0), Greenpos.get(1)).equals("GREEN")){
            NumCompletedTasks++;
            CheckColor.add(5,1);
        }

        return NumCompletedTasks;
    }
}
