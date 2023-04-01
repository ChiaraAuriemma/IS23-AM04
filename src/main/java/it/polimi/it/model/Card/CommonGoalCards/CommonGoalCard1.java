package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommonGoalCard1 extends CommonGoalCard{

    private List<Tile> pink;
    private List<Tile> cyan;
    private List<Tile> yellow;
    private List<Tile> blue;
    private List<Tile> white;
    private List<Tile> green;

    public CommonGoalCard1(int id){
        super(id);
        this.pink = new ArrayList<Tile>(8);
        this.cyan = new ArrayList<Tile>(8);
        this.yellow = new ArrayList<Tile>(8);
        this.blue = new ArrayList<Tile>(8);
        this.white = new ArrayList<Tile>(8);
        this.green = new ArrayList<Tile>(8);
    }

    public Boolean checkGoal(Shelfie shelfie){
        int column,row;

        for(column=0;column<5;column++){
            for(row=0; row<6; row++){
                Tile tile1 = shelfie.getCell(column,row);
                Tile tile2 = shelfie.getCell(column+1,row);
                Tile tile3 = shelfie.getCell(column +1,row +1);
                Tile tile4 = shelfie.getCell(column,row +1);
                if(tile1.getColor().equals(tile2.getColor()) && tile2.getColor().equals(tile3.getColor()) && tile3.getColor().equals(tile4.getColor())){

                    if(tile1.getColor().equals("PINK")){
                        if(!pink.contains(tile1) && !pink.contains(tile2) && !pink.contains(tile3) && !pink.contains(tile4)){
                            pink.add(tile1);
                            pink.add(tile2);
                            pink.add(tile3);
                            pink.add(tile4);
                        }
                    }
                    if(shelfie.getCell(column,row).getColor().equals("CYAN")){
                        if(!cyan.contains(tile1) && !cyan.contains(tile2) && !cyan.contains(tile3) && !cyan.contains(tile4)){
                            cyan.add(tile1);
                            cyan.add(tile2);
                            cyan.add(tile3);
                            cyan.add(tile4);
                        }
                    }
                    if(shelfie.getCell(column,row).getColor().equals("YELLOW")){
                        if(!yellow.contains(tile1) && !yellow.contains(tile2) && !yellow.contains(tile3) && !yellow.contains(tile4)){
                            yellow.add(tile1);
                            yellow.add(tile2);
                            yellow.add(tile3);
                            yellow.add(tile4);
                        }
                    }
                    if(shelfie.getCell(column,row).getColor().equals("BLUE")){
                        if(!blue.contains(tile1) && !blue.contains(tile2) && !blue.contains(tile3) && !blue.contains(tile4)){
                            blue.add(tile1);
                            blue.add(tile2);
                            blue.add(tile3);
                            blue.add(tile4);
                        }
                    }
                    if(shelfie.getCell(column,row).getColor().equals("WHITE")){
                        if(!white.contains(tile1) && !white.contains(tile2) && !white.contains(tile3) && !white.contains(tile4)){
                            white.add(tile1);
                            white.add(tile2);
                            white.add(tile3);
                            white.add(tile4);
                        }
                    }
                    if(shelfie.getCell(column,row).getColor().equals("GREEN")){
                        if(!green.contains(tile1) && !green.contains(tile2) && !green.contains(tile3) && !green.contains(tile4)){
                            green.add(tile1);
                            green.add(tile2);
                            green.add(tile3);
                            green.add(tile4);
                        }
                    }

                }
                if(pink.size() == 8 || cyan.size() == 8 || yellow.size() == 8 ||  blue.size() == 8 ||  white.size() == 8 ||  green.size() == 8 ){
                    return true;
                }
            }
        }

        return false;
    }
}
