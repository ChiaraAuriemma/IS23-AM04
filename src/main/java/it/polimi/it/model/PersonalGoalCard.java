package it.polimi.it.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PersonalGoalCard extends Card{
    private Integer id;
    private String name;
    private Integer NumCompletedTasks;
    private HashMap<PossibleColors, ArrayList<Integer>> Score;
    private ArrayList<Integer> Position0;
    private ArrayList<Integer> Position1;
    private ArrayList<Integer> Position2;
    private ArrayList<Integer> Position3;
    private ArrayList<Integer> Position4;
    private ArrayList<Integer> Position5;

    public PersonalGoalCard(){
        this.name = "Personal";
        this.NumCompletedTasks = 0;
    }

    public Integer CheckScore(){
        //devo controllare la shelfie nei punti specifici della mia carta
        if(Shelfie.getPersonalGoalCard = id){

    }
}
