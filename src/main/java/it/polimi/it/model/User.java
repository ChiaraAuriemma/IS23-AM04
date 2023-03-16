package it.polimi.it.model;

public class User {

    private String Nickname;

    private int Score;

    private boolean InGame;

    private int Count;

    private Tile[] ChoosenTiles;


    public  User(){

        this.Nickname = setNickname();

        this.Score = 0;

        this.Count = 0;

        this.InGame = true;

        this.ChoosenTiles = new Tile[4];

    }
    private void Play(){

        //lock
        ChoosenTiles =  Board.ChooseTiles();
        //unlock

        int count = Board.getCount();

        int column = Shelfie.ChooseColumn();

        Shelfie.AddTile(column, ChoosenTiles, count);

        Board.Refill();





    }

    public String getNickname() {
        return Nickname;
    }


    public int getScore() {
        return Score;
    }

    public String setNickname(){
        //scanf

        return Nickname;
    }

    public int getCount() {
        return Count;
    }

    public boolean checkInGame(){
        //check se crasha

        return InGame;
    }
}
