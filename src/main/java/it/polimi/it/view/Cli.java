package it.polimi.it.view;

import it.polimi.it.model.Tiles.Tile;

import java.util.Scanner;

import static java.lang.System.out;



public class Cli {
    private Scanner scanner;
    //public static final String ANSI_RESET = "\u001B[0m";

    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PINK_BACKGROUND = "\u001B[45m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
   // public static final String CYAN_BACKGROUND = "\u001B[46m";


/*
    BLACK	\u001B[30m	BLACK_BACKGROUND	\u001B[40m
    RED	\u001B[31m	RED_BACKGROUND	\u001B[41m
    GREEN	\u001B[32m	GREEN_BACKGROUND	\u001B[42m
    YELLOW	\u001B[33m	YELLOW_BACKGROUND	\u001B[43m
    BLUE	\u001B[34m	BLUE_BACKGROUND	\u001B[44m
    PURPLE	\u001B[35m	PURPLE_BACKGROUND	\u001B[45m
    CYAN	\u001B[36m	CYAN_BACKGROUND	\u001B[46m
    WHITE	\u001B[37m	WHITE_BACKGROUND	\u001B[47m

    BLACK	\u001B[30m	BLACK_BACKGROUND	\u001B[40m
    RED	\u001B[31m	RED_BACKGROUND	\u001B[41m
    GREEN	\u001B[32m	GREEN_BACKGROUND	\u001B[42m
    YELLOW	\u001B[33m	YELLOW_BACKGROUND	\u001B[43m
    BLUE	\u001B[34m	BLUE_BACKGROUND	\u001B[44m
    PURPLE	\u001B[35m	PURPLE_BACKGROUND	\u001B[45m
    CYAN	\u001B[36m	CYAN_BACKGROUND	\u001B[46m
    WHITE	\u001B[37m	WHITE_BACKGROUND	\u001B[47m

    */




    public void Title() {
        String title =  "▒█▀▄▀█ █░░█ 　 ▒█▀▀▀█ █░░█ █▀▀ █░░ █▀▀ ░▀░ █▀▀ \n" +
                        "▒█▒█▒█ █▄▄█ 　 ░▀▀▀▄▄ █▀▀█ █▀▀ █░░ █▀▀ ▀█▀ █▀▀ \n" +
                        "▒█░░▒█ ▄▄▄█ 　 ▒█▄▄▄█ ▀░░▀ ▀▀▀ ▀▀▀ ▀░░ ▀▀▀ ▀▀▀ \n";
        out.println(title);
    }


    /*

    ⲘႸ ⟆ᖺ∈𝘭⨍⫯∈

    🄼🅈 🅂🄷🄴🄻🄵🄸🄴


███╗░░░███╗██╗░░░██╗░░░░░░░░░██████╗██╗░░██╗███████╗██╗░░░░░███████╗██╗███████╗
████╗░████║╚██╗░██╔╝░░░░░░░░██╔════╝██║░░██║██╔════╝██║░░░░░██╔════╝██║██╔════╝
██╔████╔██║░╚████╔╝░░░░░░░░░╚█████╗░███████║█████╗░░██║░░░░░█████╗░░██║█████╗░░
██║╚██╔╝██║░░╚██╔╝░░░░░░░░░░░╚═══██╗██╔══██║██╔══╝░░██║░░░░░██╔══╝░░██║██╔══╝░░
██║░╚═╝░██║░░░██║░░░░░░░░░░░░██████╔╝██║░░██║███████╗███████╗██║░░░░░██║███████╗
╚═╝░░░░░╚═╝░░░╚═╝░░░░░░░░░░░╚═════╝░╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░╚═╝╚══════╝






                                      _,~,
           ¿─╖_   ─:╥                ╔░▄╠Ä░µ ___            _,  ._;
           ]░▐    ░░▀                ▌░`╙φ▄▀  W╫╛           Γ▓ ▒▄▀░,░µ
           j░░¥  ▒░░⌐  _.-∩w  ∩jµ    └░▒_     Ñ╬            L▓]░▌ ╫▄▄`
           j▒▌▒ç(▄$░⌐   "N░,,▒ÿ▀      _▒░▒─   b░▒▒▒╗        ▒▓_╡E   ╬▒r
           j▒▓"▒▐`╢▒⌐     ╚░░▄╩          '▒▒µ ▒░▀']▒▌ ▒▌""▒⌐▒▓]▄░▒Å m▒⌐┌Ñ▀""▒
           j▒▓_▄▄ ╢▒ ,ΘH╥,╓▒▓       ▒▒▒╢⌐ ▒▒▓ ▒▐  ]▒M╢▒▀╨▀▀^▒▓  ╟▒  ▓▒ ▌▐╨╨▀▀`
           ╠▒▒   ╓▒▒,▐▒▒▒╢▒▀        ╙▒▒▒▌╢▒▓▀_▒▒  ╢▒,└╢╖,╢▒▌╢▓  ╟▒U ▓▒ ╚▒╖╓▒▒Γ
           ╙╙╙"  ╙╙╙" "╨╨▀▀           ╙▀╜▀▀  _╙╙"_╙╙╙  ╙╙╙` ╙╙  ╙╙" ╙╙`  ╙╙╙'


---
asciiart.club

     */

    private void readInitializer()
    {
        this.scanner = new Scanner(System.in);
    }

    public String readString(){
        readInitializer();
        String givenInput;
        try {
            givenInput = scanner.nextLine();
            return givenInput;
        } catch (Exception e) {
            System.out.println(e.getMessage() + "Retry: ");
            givenInput = scanner.nextLine();
            return givenInput;
        }
    }


    public int readInt(){
        int read;
        readInitializer();
        try{
            read = scanner.nextInt();
            return read;
        }catch (Exception e){
            System.out.println(e.getMessage() + "Retry: ");
            read = scanner.nextInt();
            return read;
        }
    }


    public void Login(){
        String message = "Please enter your nickname";
        out.println(message);
        String chosenNickname = readString();

        while(/*faccio i controlli sul nickname ricevuto
                    -> mentre faccio i controlli se è buono lo salvo in lobby */ chosenNickname.equals("JackB")){
            String messageRetry = "This nickname is already taken, try with another one";
            out.println(messageRetry);
            chosenNickname = readString();
        }

        String welcomeMessage = "Welcome, " + chosenNickname + "!\n";
        out.println(welcomeMessage);
        return;
    }

    public void JoinOrCreate(){
        String message = "Do you want to join or create a new Game?";
        out.println(message);
        String response = readString();

        while(!response.equals("create") && !response.equals("join") && !response.equals("Create")  &&
              !response.equals("CREATE") && !response.equals("JOIN") && !response.equals("Join")){
            String messageRetry = "This nickname is already taken, try with another one";
            out.println(messageRetry);
            response = readString();
        }

        if(response.equals("create") || response.equals("Create")  || response.equals("CREATE") ){
            Join();
        } else{
            Create();
        }
        return;
    }

    public void Join() {
        String message = "Insert the ID of the game you'd like to join... ";
        out.println(message);
        int response = readInt();

       // while(/*controlla se l'ID esiste*/){
            //nel messaggio che mando per controllare l'id, se è valido joino!
         //   ){

            String messageRetry = "You can't join this Game! Retry with another ID... ";
            out.println(messageRetry);
            response = readInt();
       // }

    }

    public void Create() {
        String message = "Insert the number of player you want... ";
        out.println(message);
        int response = readInt();

        while(response<2 || response>4){
            String messageRetry = "You can't create a Game with less than 2 or more than 4 players! Retry... ";
            out.println(messageRetry);
            response = readInt();
        }

        //mando messaggio creazione game
        return;
    }

    public void PrintBoard(Tile[][] matrix){
        for (int i= 0 ; i< 11; i++){
            for (int j=0; j<11; j++){
                if(i==0 && j==0){
                    out.print(" ");
                } else if (i==0){
                    out.print(j-1);
                } else if (j==0) {
                    out.print(i-1);
                } else if (j==10) {
                    out.print("\n");
                }else if (i==10) {
                    out.print(j-1);
                } else{
                    switch (matrix[i-1][j-1].getColor()){
                        case "GREEN": out.print(GREEN_BACKGROUND);
                        case "BLUE": out.print(BLUE_BACKGROUND);
                        case "PINK": out.print(PINK_BACKGROUND);
                        case "YELLOW": out.print(YELLOW_BACKGROUND);
                        case "CYAN": out.print(CYAN_BACKGROUND);
                        case "WHITE": out.print(WHITE_BACKGROUND);
                        case "XTILE": out.print(" ");
                        case "DEFAULT": out.print(" ");
                    }
                }
            }
        }
    }
}
