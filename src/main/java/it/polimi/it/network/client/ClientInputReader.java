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

    /**
     * "RMI" or "TCP" string, used to know how to choose between the TCP-message or the RMI-method
     */
    private String connectionType;

   private ClientInterface client;
    /**
     * Instance of the View class
     */
    private View view;


    /**
     * Method to constantly read the input given by the user, sends the input line to the command parser
     */
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


    /**
     * Method used to parse the input given by the user, recognizing a certain pattern
     * @param input is the String read by the buffer
     * @throws RemoteException .
     */
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
                    client.login(nickname);
                    break;

                case "create_game":// create_game>>4
                    int numPlayers = Integer.parseInt(input.substring(matcher.end()));

                    client.createGame(numPlayers);
                    break;

                case "join_game": //join_game>>gameID
                    int gameID = Integer.parseInt(input.substring(matcher.end()));

                   client.joinGame(gameID);
                   break;

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


                   client.tilesNumMessage(numTiles);
                   break;

                case "take_tiles": // tiles; format TBD (0,2);(1,3);(4,7)
                    String chosenTiles = input.substring(matcher.end());

                    // tiles; format TBD (0,2);(1,3);(4,7) -> lunghezza <6-> 1 tile; <12->2tiles; else->3tiles

                    client.getView();
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

                    client.selectedTiles(chosenTilesList);
                break;

                case "choose_column": //choose_column>>number of the column; TBD:le colonne partono da 0 o da 1????
                    int column = Integer.parseInt(input.substring(matcher.end()));


                    client.chooseColumn(column);
                    break;


                case "help":view.printCommands();break;


                default : System.out.println("Error! No valid message type detected!");
            }
        }
    }

    /**
     * Setter method
     * @param connectionType is a String, either "RMI" or "TCP", used to know which type of message
     *                       has to be sent
     */
    public void setConnectionType(String connectionType) throws IOException, NotBoundException {
        this.connectionType = connectionType;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/resources/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);
        if (connectionType.equals("TCP")){
            client = new ClientTCP(jsonObject.get("portTCP").getAsInt(),jsonObject.get("ip").getAsString());
            client.startClient();
        }if(connectionType.equals("RMI")){
            client = new ClientRMIApp(jsonObject.get("portRMI").getAsInt(),jsonObject.get("ip").getAsString());
            client.startClient();
        }
    }

}
