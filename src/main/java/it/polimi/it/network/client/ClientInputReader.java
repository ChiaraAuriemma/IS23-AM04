package it.polimi.it.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.View;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientInputReader implements Runnable{
    private volatile boolean running;
    private String connectionType;
    private View view;

    private ClientInterface client;


    @Override
    public void run() {
        running = true;


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (running) {
            try {
                String input = reader.readLine();
                commandParser(input);
                if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
                    running = false;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void commandParser(String input) throws RemoteException {
        String regex = "(\\w+)>>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String command = matcher.group(1);
            command = command.toLowerCase();


            switch (command) {
                case "login": //initial nickname setting
                    String nickname = input.substring(matcher.end());
                    //metodo che manda il messaggio login
                            client.createPlayer(nickname);
                    ;break;

                case "create_game":// create_game>>4
                    int numPlayers = Integer.parseInt(input.substring(matcher.end()));


                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            clientTCP.createGame(numPlayers);
                            break;
                        case "rmi": //chiamata metodi RMI
                            clientRMIApp.createGame(numPlayers);
                            break;
                    }
                    ;break;

                case "join_game": //join_game>>gameID
                    int gameID = Integer.parseInt(input.substring(matcher.end()));

                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            clientTCP.joinGame(gameID);
                            break;
                        case "rmi": //chiamata metodi RMI
                            clientRMIApp.joinGame(gameID);
                            break;
                    }
                    ;break;

                case "chat": //chat>>Write here your message...
                    String chatMessage = input.substring(matcher.end());


                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            ;break;
                        case "rmi": //chiamata metodi RMI
                            ;break;
                    }
                    ;break;



               /* case "connection": //connection>>RMI     or TCP
                    String connectionType = input.substring(matcher.end());
                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            ;break;
                        case "rmi": //chiamata metodi RMI
                            ;break;
                    }
                    ;break;
                */


                case "num_tiles": //num_tiles>> number of tiles you want to get from the board
                    int numTiles = Integer.parseInt(input.substring(matcher.end()));


                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            clientTCP.tilesNum(numTiles);
                            break;
                        case "rmi": //chiamata metodi RMI
                            clientRMIApp.tilesNumMessage(numTiles);
                            break;
                    }
                    ;break;

                case "take_tiles": // tiles; format TBD (0,2);(1,3);(4,7)
                    String chosenTiles = input.substring(matcher.end());

                    // tiles; format TBD (0,2);(1,3);(4,7) -> lunghezza <6-> 1 tile; <12->2tiles; else->3tiles

                    switch (connectionType){
                        case "tcp":
                            view=clientTCP.getView();
                            break;
                        case "rmi":
                            view=clientRMIApp.getView();
                            break;
                    }
                    ArrayList<Tile> chosenTilesList=new ArrayList<>();
                    char[] chosen = chosenTiles.toCharArray();
                    int row=Character.getNumericValue(chosen[1]);
                    int col=Character.getNumericValue(chosen[3]);
                    Tile t = new Tile(row, col, PossibleColors.valueOf(view.getTileColor(row, col)));
                    chosenTilesList.add(t);

                    if (chosenTiles.length()>6){
                        row=Character.getNumericValue(chosen[7]);
                        col=Character.getNumericValue(chosen[9]);
                        t = new Tile(row, col, PossibleColors.valueOf(view.getTileColor(row, col)));
                        chosenTilesList.add(t);

                        if (chosenTiles.length()>12){
                            row=Character.getNumericValue(chosen[13]);
                            col=Character.getNumericValue(chosen[15]);
                            t = new Tile(row, col, PossibleColors.valueOf(view.getTileColor(row, col)));
                            chosenTilesList.add(t);
                        }
                    }

                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            clientTCP.selectedTiles(chosenTilesList);
                            break;
                        case "rmi": //chiamata metodi RMI
                            clientRMIApp.selectedTiles(chosenTilesList);
                            break;
                    }
                    ;break;

                case "choose_column": //choose_column>>number of the column; TBD:le colonne partono da 0 o da 1????
                    int column = Integer.parseInt(input.substring(matcher.end()));


                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            ;
                            break;
                        case "rmi": //chiamata metodi RMI
                            ;break;
                    }
                    ;break;

                case "order": //reorders the tiles; TBD il formato: tipo 1-3-2 per riordinare le tiles prima->terza->seconda nella lista
                    String order = input.substring(matcher.end());



                    switch (connectionType){
                        case "tcp": //invio messaggio TCP
                            ;break;
                        case "rmi": //chiamata metodi RMI
                            ;break;
                    }
                    ;break;


                case "help":view.printCommands();break;


                default : System.out.println("Error! No valid message type detected!");
            }
        }
    }

    public void setConnectionType(String connectionType) throws IOException, NotBoundException {
        this.connectionType = connectionType;

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/java/it/polimi/it/network/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);

        if(connectionType.equals("TCP")){
            this.client = new ClientTCP2(jsonObject.get("portTCP").getAsInt(),jsonObject.get("ip").getAsString());
            client.startClient();
        }else{
            this.client = new ClientRMIApp(jsonObject.get("portRMI").getAsInt(),jsonObject.get("ip").getAsString());
            client.startClient();
        }
    }

}
