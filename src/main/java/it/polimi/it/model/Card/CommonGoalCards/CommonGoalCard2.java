package it.polimi.it.model.Card.CommonGoalCards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Shelfie;

import java.io.FileReader;

public class CommonGoalCard2 extends CommonGoalCard{

    private int jsonIndex;
    public CommonGoalCard2(int id){ //carte 2 e 6
        super();
        this.id = id;
    }

    public Boolean checkGoal(Shelfie shelfie){
        Gson gson =  new Gson();
        int j;
        int i;
        int stop;
        int numRight = 0;
        String cell1, cell2;
        try{
            JsonReader reader = new JsonReader(new FileReader("CommonGoalCard2.json"));
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            for(j=0; jsonArray.get(j).getAsJsonObject().get("type").getAsInt() != id ; j++);
            JsonObject jsonObject = jsonArray.get(j).getAsJsonObject();


            for(i=0; i<jsonObject.get("i").getAsInt() && numRight<2; i++) {
                stop = 0;
                for(j=0; j<jsonObject.get("j").getAsInt() && stop == 0; j++){
                    if(jsonObject.get("type").getAsInt() == 2){
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
