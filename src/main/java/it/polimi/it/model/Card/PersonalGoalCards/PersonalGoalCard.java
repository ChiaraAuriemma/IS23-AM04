package it.polimi.it.model.Card.PersonalGoalCards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Shelfie;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PersonalGoalCard {

    private int numCompletedTasks;
    private List<Integer> checkColor;
    JsonArray pinkPos;
    JsonArray cyanPos;
    JsonArray yellowPos;
    JsonArray bluePos;
    JsonArray whitePos;
    JsonArray greenPos;

    /**
     * constructor for the PersonalCards
     * @param id is the identification code of the card we need to instantiate
     */
    public PersonalGoalCard(int id){
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/java/it/polimi/it/model/Card/PersonalGoalCards/PersonalGoalCards.json"));
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            JsonObject jsonObject = jsonArray.get(id-1).getAsJsonObject();
            pinkPos = jsonObject.getAsJsonArray("pinkPos");
            cyanPos = jsonObject.getAsJsonArray("cyanPos");
            yellowPos = jsonObject.getAsJsonArray("yellowPos");
            bluePos = jsonObject.getAsJsonArray("bluePos");
            whitePos = jsonObject.getAsJsonArray("whitePos");
            greenPos = jsonObject.getAsJsonArray("greenPos");
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
        return pinkPos.get(0).getAsInt();
    }

    /**
     * getter method
     * @return the row of the pink tile of the goal
     */
    public int getPinkposRow(){
        return pinkPos.get(1).getAsInt();
    }

    /**
     * getter method
     * @return the column of the cyan tile of the goal
     */
    public int getCyanposColumn(){
        return cyanPos.get(0).getAsInt();
    }

    /**
     * getter method
     * @return the row of the cyan tile of the goal
     */
    public int getCyanposRow(){
        return cyanPos.get(1).getAsInt();
    }

    /**
     * getter method
     * @return the column of the yellow tile of the goal
     */
    public int getYellowposColumn(){
        return yellowPos.get(0).getAsInt();
    }

    /**
     * getter method
     * @return the row of the yellow tile of the goal
     */
    public int getYellowposRow(){
        return yellowPos.get(1).getAsInt();
    }

    /**
     * getter method
     * @return the column of the blue tile of the goal
     */
    public int getBlueposColumn(){
        return bluePos.get(0).getAsInt();
    }

    /**
     * getter method
     * @return the row of the blue tile of the goal
     */
    public int getBlueposRow(){
        return bluePos.get(1).getAsInt();
    }

    /**
     * getter method
     * @return the column of the white tile of the goal
     */
    public int getWhiteposColumn(){
        return whitePos.get(0).getAsInt();
    }

    /**
     * getter method
     * @return the row of the white tile of the goal
     */
    public int getWhiteposRow(){
        return whitePos.get(1).getAsInt();
    }

    /**
     * getter method
     * @return the column of the green tile of the goal
     */
    public int getGreenposColumn(){
        return greenPos.get(0).getAsInt();
    }

    /**
     * getter method
     * @return the row of the green tile of the goal
     */
    public int getGreenposRow(){
        return greenPos.get(1).getAsInt();
    }

    /**
     * Method that check the goal of the PersonalGoalCard
     * @param shelfie is the shelfie that we need to check
     * @return the number of the color of the goal achieved by the player
     */
    public int checkScore(Shelfie shelfie) {
        if(checkColor.get(0) == 0 && shelfie.getCell(pinkPos.get(0).getAsInt(), pinkPos.get(1).getAsInt()).getColor().equals("PINK")){
            numCompletedTasks++;
            checkColor.add(0,1);
        }
        if(checkColor.get(1) == 0 && shelfie.getCell(cyanPos.get(0).getAsInt(), cyanPos.get(1).getAsInt()).getColor().equals("CYAN")){
            numCompletedTasks++;
            checkColor.add(1,1);
        }
        if(checkColor.get(2)== 0 && shelfie.getCell(yellowPos.get(0).getAsInt(), yellowPos.get(1).getAsInt()).getColor().equals("YELLOW")){
            numCompletedTasks++;
            checkColor.add(2,1);
        }
        if(checkColor.get(3) == 0 && shelfie.getCell(bluePos.get(0).getAsInt(), bluePos.get(1).getAsInt()).getColor().equals("BLUE")){
            numCompletedTasks++;
            checkColor.add(3,1);
        }
        if(checkColor.get(4) == 0 && shelfie.getCell(whitePos.get(0).getAsInt(), whitePos.get(1).getAsInt()).getColor().equals("WHITE")){
            numCompletedTasks++;
            checkColor.add(4,1);
        }
        if(checkColor.get(5) == 0 && shelfie.getCell(greenPos.get(0).getAsInt(), greenPos.get(1).getAsInt()).getColor().equals("GREEN")){
            numCompletedTasks++;
            checkColor.add(5,1);
        }

        return numCompletedTasks;
    }
}
