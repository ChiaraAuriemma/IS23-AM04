/*package it.polimi.it.model.Card.CommonGoalCards;

import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;

import java.util.Optional;

public class CommonGoalCard1 extends CommonGoalCard{

    private Optional<Tile> tmpPink;
    private Optional<Tile> tmpCyan;
    private Optional<Tile> tmpYellow;
    private Optional<Tile> tmpBlue;
    private Optional<Tile> tmpWhite;
    private Optional<Tile> tmpGreen;

    public CommonGoalCard1(){
        super();
        this.id = 1;
    }

    public Boolean checkGoal(Shelfie shelfie){
        int column,row;
        for(column=0;column<5;column++){
            for(row=0; row<6; row++){
                if(shelfie.getCell(column,row).equals(shelfie.getCell(column+1,row))
                        && shelfie.getCell(column +1, row).equals(shelfie.getCell(column +1,row +1 ))
                        && shelfie.getCell(column+1,row+1).equals(shelfie.getCell(column,row +1))){

                    /*if(shelfie.getCell(column,row).equals("PINK")){
                        if(!tmpPink.isPresent())
                            tmpPink =
                    }

                }
            }
        }
    }
        return false;
}
*/