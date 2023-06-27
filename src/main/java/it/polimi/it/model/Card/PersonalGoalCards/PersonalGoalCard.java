package it.polimi.it.model.Card.PersonalGoalCards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Shelfie;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class that manages personal goal cards and their points
 */
public class PersonalGoalCard implements Serializable {

    private static final long serialVersionUID = -3897925876914460232L;
    private int numCompletedTasks;
    private int id;
    private List<Integer> checkColor;

    private ArrayList<Integer> pinkPos;
    private ArrayList<Integer> cyanPos;
    private ArrayList<Integer> yellowPos;
    private ArrayList<Integer> bluePos;
    private ArrayList<Integer> whitePos;
    private ArrayList<Integer> greenPos;


    /**
     * constructor for the PersonalCards
     * @param id is the identification code of the card we need to instantiate
     */
    public PersonalGoalCard(int id){
        this.id = id;
        pinkPos = new ArrayList<>(Arrays.asList(0, 0));
        cyanPos = new ArrayList<>(Arrays.asList(0, 0));
        yellowPos = new ArrayList<>(Arrays.asList(0, 0));
        bluePos = new ArrayList<>(Arrays.asList(0, 0));
        whitePos = new ArrayList<>(Arrays.asList(0, 0));
        greenPos = new ArrayList<>(Arrays.asList(0, 0));
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader((new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("PersonalGoalCards.json")))));
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            JsonObject jsonObject = jsonArray.get(id-1).getAsJsonObject();

            jsonArray = jsonObject.getAsJsonArray("pinkPos");
            pinkPos.set(0, jsonArray.get(0).getAsInt());
            pinkPos.set(1, jsonArray.get(1).getAsInt());

            jsonArray = jsonObject.getAsJsonArray("cyanPos");
            cyanPos.set(0, jsonArray.get(0).getAsInt());
            cyanPos.set(1, jsonArray.get(1).getAsInt());

            jsonArray = jsonObject.getAsJsonArray("yellowPos");
            yellowPos.set(0, jsonArray.get(0).getAsInt());
            yellowPos.set(1, jsonArray.get(1).getAsInt());

            jsonArray = jsonObject.getAsJsonArray("bluePos");
            bluePos.set(0, jsonArray.get(0).getAsInt());
            bluePos.set(1, jsonArray.get(1).getAsInt());

            jsonArray = jsonObject.getAsJsonArray("whitePos");
            whitePos.set(0, jsonArray.get(0).getAsInt());
            whitePos.set(1, jsonArray.get(1).getAsInt());

            jsonArray = jsonObject.getAsJsonArray("greenPos");
            greenPos.set(0, jsonArray.get(0).getAsInt());
            greenPos.set(1, jsonArray.get(1).getAsInt());

            this.numCompletedTasks = 0;
            this.checkColor = new ArrayList<Integer>(6);
            int i;
            for(i=0; i<6; i++){
                this.checkColor.add(i,0);
            }
            reader.close();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * getter method
     * @return the column of the pink tile of the goal
     */
    public int getPinkposColumn(){
        return pinkPos.get(0);//.getAsInt();
    }


    /**
     * getter method
     * @return the row of the pink tile of the goal
     */
    public int getPinkposRow(){
        return pinkPos.get(1);//.getAsInt();
    }


    /**
     * getter method
     * @return the column of the cyan tile of the goal
     */
    public int getCyanposColumn(){
        return cyanPos.get(0);//.getAsInt();
    }


    /**
     * getter method
     * @return the row of the cyan tile of the goal
     */
    public int getCyanposRow(){
        return cyanPos.get(1);//.getAsInt();
    }


    /**
     * getter method
     * @return the column of the yellow tile of the goal
     */
    public int getYellowposColumn(){
        return yellowPos.get(0);//.getAsInt();
    }


    /**
     * getter method
     * @return the row of the yellow tile of the goal
     */
    public int getYellowposRow(){
        return yellowPos.get(1);//.getAsInt();
    }


    /**
     * getter method
     * @return the column of the blue tile of the goal
     */
    public int getBlueposColumn(){
        return bluePos.get(0);//.getAsInt();
    }


    /**
     * getter method
     * @return the row of the blue tile of the goal
     */
    public int getBlueposRow(){
        return bluePos.get(1);//.getAsInt();
    }


    /**
     * getter method
     * @return the column of the white tile of the goal
     */
    public int getWhiteposColumn(){
        return whitePos.get(0);//.getAsInt();
    }


    /**
     * getter method
     * @return the row of the white tile of the goal
     */
    public int getWhiteposRow(){
        return whitePos.get(1);//.getAsInt();
    }


    /**
     * getter method
     * @return the column of the green tile of the goal
     */
    public int getGreenposColumn(){
        return greenPos.get(0);//.getAsInt();
    }


    /**
     * getter method
     * @return the row of the green tile of the goal
     */
    public int getGreenposRow(){
        return greenPos.get(1);//.getAsInt();
    }


    /**
     * Method that check the goal of the PersonalGoalCard
     * @param shelfie is the shelfie that we need to check
     * @return the number of the color of the goal achieved by the player
     */
    public int checkScore(Shelfie shelfie) {
        if(checkColor.get(0) == 0 && shelfie.getCell(pinkPos.get(0), pinkPos.get(1)).getColor().equals("PINK")){
            numCompletedTasks++;
            checkColor.set(0,1);
        }
        if(checkColor.get(1) == 0 && shelfie.getCell(cyanPos.get(0), cyanPos.get(1)).getColor().equals("CYAN")){
            numCompletedTasks++;
            checkColor.set(1,1);
        }
        if(checkColor.get(2)== 0 && shelfie.getCell(yellowPos.get(0), yellowPos.get(1)).getColor().equals("YELLOW")){
            numCompletedTasks++;
            checkColor.set(2,1);
        }
        if(checkColor.get(3) == 0 && shelfie.getCell(bluePos.get(0), bluePos.get(1)).getColor().equals("BLUE")){
            numCompletedTasks++;
            checkColor.set(3,1);
        }
        if(checkColor.get(4) == 0 && shelfie.getCell(whitePos.get(0), whitePos.get(1)).getColor().equals("WHITE")){
            numCompletedTasks++;
            checkColor.set(4,1);
        }
        if(checkColor.get(5) == 0 && shelfie.getCell(greenPos.get(0), greenPos.get(1)).getColor().equals("GREEN")){
            numCompletedTasks++;
            checkColor.set(5,1);
        }
        return numCompletedTasks;
    }


    /**
     * Getter method
     * @return the id of this Card.
     */
    public int getId() {
        return id;
    }
}
