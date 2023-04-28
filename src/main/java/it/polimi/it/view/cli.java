package it.polimi.it.view;

import it.polimi.it.controller.Lobby;
import it.polimi.it.model.Tiles.Tile;

import java.util.Scanner;

import static java.lang.System.out;



public class cli {
    private Scanner scanner;

    public void Title() {
        String title =  "▒█▀▄▀█ █░░█ 　 ▒█▀▀▀█ █░░█ █▀▀ █░░ █▀▀ ░▀░ █▀▀ \n" +
                        "▒█▒█▒█ █▄▄█ 　 ░▀▀▀▄▄ █▀▀█ █▀▀ █░░ █▀▀ ▀█▀ █▀▀ \n" +
                        "▒█░░▒█ ▄▄▄█ 　 ▒█▄▄▄█ ▀░░▀ ▀▀▀ ▀▀▀ ▀░░ ▀▀▀ ▀▀▀ \n";
        out.println(title);
    }

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

        while(/*controlla se l'ID esiste*/){
            //nel messaggio che mando per controllare l'id, se è valido joino!

            String messageRetry = "You can't join this Game! Retry with another ID... ";
            out.println(messageRetry);
            response = readInt();
        }
        return;
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
                        case "GREEN": out.print("G");
                        case "BLUE": out.print("B");
                        case "PINK": out.print("P");
                        case "YELLOW": out.print("Y");
                        case "CYAN": out.print("C");
                        case "WHITE": out.print("W");
                        case "XTILE": out.print(" ");
                        case "DEFALT": out.print(" ");
                    }
                }
            }
        }
    }
}
