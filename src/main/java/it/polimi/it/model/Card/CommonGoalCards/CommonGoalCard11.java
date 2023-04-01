package it.polimi.it.model.Card.CommonGoalCards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Shelfie;

import java.io.FileReader;
import java.util.List;

public class CommonGoalCard11 extends CommonGoalCard{

    public CommonGoalCard11(int id){ //carte 8 e 11
        super(id);
    }

    public Boolean checkGoal(Shelfie shelfie){
        int i,j;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("CommonGoalCard12.json"));
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            for(j=0; jsonArray.get(j).getAsJsonObject().get("type").getAsInt() != id ; j++);
            for(i=j;jsonArray.get(i).getAsJsonObject().get("type").getAsInt() == id;i++){
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String cell1 = shelfie.getCell(jsonObject.get("mustBeEqual1").getAsJsonArray().get(0).getAsInt(),jsonObject.get("mustBeEqual1").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell2 = shelfie.getCell(jsonObject.get("mustBeEqual2").getAsJsonArray().get(0).getAsInt(),jsonObject.get("mustBeEqual2").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell3 = shelfie.getCell(jsonObject.get("mustBeEqual3").getAsJsonArray().get(0).getAsInt(),jsonObject.get("mustBeEqual3").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell4 = shelfie.getCell(jsonObject.get("mustBeEqual4").getAsJsonArray().get(0).getAsInt(),jsonObject.get("mustBeEqual4").getAsJsonArray().get(1).getAsInt()).getColor();
                if(jsonObject.get("exception").getAsInt() == 1){
                    String cell5 = shelfie.getCell(jsonObject.get("mustBeEqual5").getAsJsonArray().get(0).getAsInt(),jsonObject.get("mustBeEqual5").getAsJsonArray().get(1).getAsInt()).getColor();
                    if(!cell1.equals("DEFAULT") && cell1.equals(cell2) && cell2.equals(cell3) && cell3.equals(cell4) && cell4.equals(cell5)){
                        return true;
                    }
                }else if(!cell1.equals("DEFAULT") &&  cell1.equals(cell2) && cell2.equals(cell3) && cell3.equals(cell4))
                    return true;

            }

            return false;

        }catch (Exception e){
            return false;
        }

    }
}

