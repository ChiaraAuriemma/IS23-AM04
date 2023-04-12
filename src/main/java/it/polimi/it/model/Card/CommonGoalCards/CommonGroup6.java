package it.polimi.it.model.Card.CommonGoalCards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Shelfie;

import java.io.FileReader;

public class CommonGroup6 extends CommonGoalCard{

    public CommonGroup6(int id){ //solo carta 12
        super(id);
    }

    public Boolean checkGoal(Shelfie shelfie){
        int i;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("CommonGroup6.json"));
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            for(i=0;i<jsonArray.size();i++){
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String cell1 = shelfie.getCell(jsonObject.get("noDefault1").getAsJsonArray().get(0).getAsInt(),jsonObject.get("noDefault1").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell2 = shelfie.getCell(jsonObject.get("noDefault2").getAsJsonArray().get(0).getAsInt(),jsonObject.get("noDefault2").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell3 = shelfie.getCell(jsonObject.get("noDefault3").getAsJsonArray().get(0).getAsInt(),jsonObject.get("noDefault3").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell4 = shelfie.getCell(jsonObject.get("noDefault4").getAsJsonArray().get(0).getAsInt(),jsonObject.get("noDefault4").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell5 = shelfie.getCell(jsonObject.get("noDefault5").getAsJsonArray().get(0).getAsInt(),jsonObject.get("noDefault5").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell6 = shelfie.getCell(jsonObject.get("yesDefault1").getAsJsonArray().get(0).getAsInt(),jsonObject.get("yesDefault1").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell7 = shelfie.getCell(jsonObject.get("yesDefault2").getAsJsonArray().get(0).getAsInt(),jsonObject.get("yesDefault2").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell8 = shelfie.getCell(jsonObject.get("yesDefault3").getAsJsonArray().get(0).getAsInt(),jsonObject.get("yesDefault3").getAsJsonArray().get(1).getAsInt()).getColor();
                String cell9 = shelfie.getCell(jsonObject.get("yesDefault4").getAsJsonArray().get(0).getAsInt(),jsonObject.get("yesDefault4").getAsJsonArray().get(1).getAsInt()).getColor();
                if(jsonObject.get("exception").getAsInt() == 1){
                    String cell10 = shelfie.getCell(jsonObject.get("yesDefault5").getAsJsonArray().get(0).getAsInt(),jsonObject.get("yesDefault5").getAsJsonArray().get(1).getAsInt()).getColor();
                    if(cell1.equals("DEFAULT") && !cell2.equals("DEFAULT") && !cell3.equals("DEFAULT") && !cell4.equals("DEFAULT") && !cell5.equals("DEFAULT")
                            && cell6.equals("DEFAULT") && cell7.equals("DEFAULT") && cell8.equals("DEFAULT") && cell9.equals("DEFAULT") && cell10.equals("DEFAULT"))
                        return true;
                }else if (!cell1.equals("DEFAULT") && !cell2.equals("DEFAULT") && !cell3.equals("DEFAULT") && !cell4.equals("DEFAULT") && !cell5.equals("DEFAULT")
                        && cell6.equals("DEFAULT") && cell7.equals("DEFAULT") && cell8.equals("DEFAULT") && cell9.equals("DEFAULT"))
                    return true;

            }

            return false;

        }catch (Exception e){
            return false;
        }

    }
}