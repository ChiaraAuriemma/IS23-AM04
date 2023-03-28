package it.polimi.it.model.Card.CommonGoalCards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Shelfie;

import java.io.FileReader;

public class CommonGoalCard2 extends CommonGoalCard{

    private int jsonIndex;
    public CommonGoalCard2(int id){
        super();
        this.id = id;
        if(id == 2)
            jsonIndex = 0;
        else jsonIndex = 1;
    }

    public Boolean checkGoal(Shelfie shelfie){
        Gson gson =  new Gson();
        try{
            JsonReader reader = new JsonReader(new FileReader("CommonGoalCard2.json"));
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            JsonObject jsonObject = jsonArray.get(jsonIndex).getAsJsonObject();
            int i,j;
            int stop;
            int numRight = 0;
            String cell1, cell2;


            for(i=0; i<jsonObject.get("i").getAsInt() && numRight<2; i++) {
                stop = 0;
                for(j=0; j<jsonObject.get("j").getAsInt() && stop == 0; j++){
                    if(jsonObject.get("type").getAsString().equals("column")){
                        cell1 = shelfie.getCell(i,j).getColor();
                        cell2 = shelfie.getCell(i+jsonObject.get("addToColumn").getAsInt(),j+jsonObject.get("addToRow").getAsInt()).getColor();
                    }else{
                        cell1 = shelfie.getCell(j,i).getColor();
                        cell2 = shelfie.getCell(j+jsonObject.get("addToColumn").getAsInt(),i+jsonObject.get("addToRow").getAsInt()).getColor();
                    }
                    if(cell1.equals(cell2) || cell1.equals("DEFAULT"))
                        stop=1;
                }
                if(stop==0)
                    numRight++;
            }


            if(numRight == 2)
                return true;
            else return false;

        }catch (Exception e){
            //
            return false ;
        }

    }
}
