package it.polimi.it.model;

public class CommonGoalCard11 extends CommonGoalCard{

    public CommonGoalCard11(){
        super();
        this.id = 11;
    }

    public Boolean CheckGoal(Shelfie shelfie){
        String Cell1 = shelfie.getCell(0,5);
        String Cell2 = shelfie.getCell(1,4);
        String Cell3 = shelfie.getCell(2,3);
        String Cell4 = shelfie.getCell(3,2);
        String Cell5 = shelfie.getCell(4,1);
        String Cell6 = shelfie.getCell(0,4);
        String Cell7 = shelfie.getCell(1,3);
        String Cell8 = shelfie.getCell(2,2);
        String Cell9 = shelfie.getCell(3,1);
        String Cell10 = shelfie.getCell(4,0);
        String Cell11 = shelfie.getCell(0,0);
        String Cell12 = shelfie.getCell(1,1);
        String Cell13 = shelfie.getCell(3,3);
        String Cell14 = shelfie.getCell(5,5);
        String Cell15 = shelfie.getCell(0,1);
        String Cell16 = shelfie.getCell(1,2);
        String Cell17 = shelfie.getCell(3,4);
        String Cell18 = shelfie.getCell(4,5);


        if(!Cell1.equals("DEFAULT") && Cell1.equals(Cell2) && Cell2.equals(Cell3) && Cell3.equals(Cell4) && Cell4.equals(Cell5)){
            return true;
        }

        if(!Cell6.equals("DEFAULT") && Cell6.equals(Cell7) && Cell7.equals(Cell8) && Cell8.equals(Cell9) && Cell9.equals(Cell10)){
            return true;
        }

        if(!Cell11.equals("DEFAULT") && Cell11.equals(Cell12) && Cell12.equals(Cell8) && Cell8.equals(Cell13) && Cell13.equals(Cell14)){
            return true;
        }

        if(!Cell15.equals("DEFAULT") && Cell15.equals(Cell16) && Cell16.equals(Cell3) && Cell3.equals(Cell17) && Cell17.equals(Cell14)){
            return true;
        }

        return false;

    }
}

