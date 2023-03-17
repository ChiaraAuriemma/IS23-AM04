package it.polimi.it.model;

public class User {

    private String Nickname;

    private int Score;

    private boolean InGame;

    private int Count;

    private Tile[] ChoosenTiles;

    private String Type;


    public User() {

        this.Nickname = Lobby.setNickname();

        this.Score = 0;

        this.Count = 0;

        this.InGame = true;

        this.ChoosenTiles = new Tile[4];

        this.Type = "GUEST";

    }

    private void Play() {

        int max = Shelfie.PossibleTiles();

        //lock
        ChoosenTiles = Board.ChooseTiles(max);
        //unlock

        int count = 0;

        for (int i = 0; i < 4 || !ChoosenTiles[i].getColor().equals("DEFAULT"); i++) {
            count++;
        }

        int column = Shelfie.ChooseColumn(count);

        Shelfie.AddTile(column, ChoosenTiles, count);

        Board.Refill();

    }

    public String getNickname() {
        return Nickname;
    }


    public int getScore() {
        return Score;
    }

    public boolean checkInGame() {
        //check se crasha

        return InGame;
    }

    void ChangeType(String Nickname) {
        Type = "Player";
    }

    /*void CreateGame(){
        comando per creare partita;
    }

    void JoinGame(){
        comando per joinare partita;
    }
     */

}
