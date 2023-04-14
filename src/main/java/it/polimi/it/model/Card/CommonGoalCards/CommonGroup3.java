package it.polimi.it.model.Card.CommonGoalCards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;

import java.io.FileReader;
import java.util.ArrayList;

public class CommonGroup3 extends CommonGoalCard{

    ArrayList<Tile> checked = new ArrayList<>();
    public CommonGroup3(int id){ //carte 3 e 4
        super(id);
    }

    private ArrayList<Tile> adjacent(Shelfie shelfie, int x, int y) {
        ArrayList<Tile> adjacent = new ArrayList<>();
        if (x > 0) {
            adjacent.add(shelfie.getCell(x-1,y));
        }
        if (y > 0) {
            adjacent.add(shelfie.getCell(x,y-1));
        }
        if (x < 4) {
            adjacent.add(shelfie.getCell(x+1,y));
        }
        if (y < 5) {
            adjacent.add(shelfie.getCell(x,y+1));
        }
        return adjacent;
    }

    private  ArrayList<Tile> recursiveAdjacent(Shelfie shelfie, int x, int y, ArrayList<Tile> toVisit, JsonObject jsonObject) {
        int i;
        ArrayList<Tile> tmp = new  ArrayList<>();
        Gson gson = new Gson();
        try {
            if(toVisit.size() == jsonObject.get("numOfTile").getAsInt()){
                for(Tile tile : toVisit){
                    if(!checked.contains(tile))
                        checked.add(tile);
                }
                return toVisit;
            }

            ArrayList<Tile> adjacent = adjacent(shelfie, x, y);
            checked.add(shelfie.getCell(x,y));
            for (Tile tile : adjacent) {
                if (!checked.contains(tile) && shelfie.getCell(x,y).getColor().equals(shelfie.getCell(tile.getColumn(),tile.getRow()).getColor())) {
                    toVisit.add(tile);
                    if(toVisit.size() == jsonObject.get("numOfTile").getAsInt()){
                        checked.add(tile);
                        return toVisit;
                    }
                    tmp.add(tile);
                }
            }

            for (Tile tile : tmp) {
                recursiveAdjacent(shelfie, tile.getColumn(), tile.getRow(), toVisit, jsonObject);
            }
            return toVisit;
        }catch (Exception e){
           throw new RuntimeException(e);
        }
    }

    public Boolean checkGoal(Shelfie shelfie){
        Gson gson = new Gson();
        ArrayList<Tile> tmp;
        int i,row, column;
        int count=0;
        try{
            JsonReader reader = new JsonReader(new FileReader("src/main/java/it/polimi/it/model/Card/CommonGoalCards/CommonGroup3.json"));
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            for(i=0; jsonArray.get(i).getAsJsonObject().get("type").getAsInt() != id ; i++);
            JsonObject jsonObject= jsonArray.get(i).getAsJsonObject();
            for(row=0; row< 6; row++){
                for(column=0; column<5; column++){
                    ArrayList<Tile> toVisit = new ArrayList<>();
                    if(checked.isEmpty() || !checked.contains(shelfie.getCell(column,row))){
                        tmp= recursiveAdjacent(shelfie, column, row,toVisit, jsonObject);
                        if(tmp.size() == jsonObject.get("numOfTile").getAsInt()){
                            count++;
                        }
                    }
                    if(count == jsonObject.get("numOfGroups").getAsInt())
                        return true;
                }
            }
            return false;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}

