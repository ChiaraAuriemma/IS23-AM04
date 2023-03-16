package it.polimi.it.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalGoalCard7 extends PersonalGoalCard{

    private Integer id;
    private HashMap<PossibleColors, ArrayList<Integer>> Score;
    private ArrayList<Integer> Position0;
    private ArrayList<Integer> Position1;
    private ArrayList<Integer> Position2;
    private ArrayList<Integer> Position3;
    private ArrayList<Integer> Position4;
    private ArrayList<Integer> Position5;


    public PersonalGoalCard7(){
        super();
        this.id = 7;
        this.Position0.add(0,0);
        this.Position0.add(1,2);
        this.Score.put(PossibleColors.CYAN,Position0);
        this.Position1.add(0,0);
        this.Position1.add(1,5);
        this.Score.put(PossibleColors.GREEN,Position1);
        this.Position2.add(0,1);
        this.Position2.add(1,3);
        this.Score.put(PossibleColors.PINK,Position2);
        this.Position3.add(0,2);
        this.Position3.add(1,0);
        this.Score.put(PossibleColors.WHITE,Position3);
        this.Position4.add(0,3);
        this.Position4.add(1,4);
        this.Score.put(PossibleColors.BLUE,Position4);
        this.Position5.add(0,4);
        this.Position5.add(1,1);
        this.Score.put(PossibleColors.YELLOW,Position5);
    }

}



