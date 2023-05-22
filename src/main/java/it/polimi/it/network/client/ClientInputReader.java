package it.polimi.it.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.View;
import it.polimi.it.view.ViewInterface;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientInputReader implements Runnable, Serializable{

    private static final long serialVersionUID = 8133446441839369630L;
    private volatile boolean running;

    /**
     * "RMI" or "TCP" string, used to know how to choose between the TCP-message or the RMI-method
     */
    private String connectionType;

   private ClientInterface client;
    /**
     * Instance of the View class
     */

    ViewInterface view;

    private TurnStages stage;

    private String lastUsedNickname;


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
        if(!input.contains(">>")){
            System.out.println("No valid commands found, please retry... ");
            return ;
        }

        String[] inp = input.split(">>");
        String command = inp[0];
        if (inp.length == 1){
            System.out.println("You didn't write anything");
            return;
        }
        if(inp[1]!=null && inp[1].length() >= 0){
            String action = inp[1];

            command = command.toLowerCase();

            if(!command.equalsIgnoreCase("chat") && !command.equalsIgnoreCase("login")){ // se è un messaggio in chat non gli tolgo gli spazi
                    action = action.replaceAll("\\s+","");
            }

            switch (command) {
                case "login"://initial nickname setting
                    if (stage == TurnStages.LOGIN) {
                        String nickname = action;
                        if(nickname.length()>12){
                            System.out.println("Nickname is too long, please retry...\n");
                            break;
                        }
                        lastUsedNickname = nickname;

                        while (nickname.length() < 12) {
                            nickname = nickname.concat(" ");
                        }

                        view.setThisNick(nickname);
                        client.login(nickname);
                    } else {
                       view.printError("There's a time and place for everything, but not now. 1");
                    } break;

                    case "create_game":// create_game>>4
                        if (stage == TurnStages.CREATEorJOIN) {
                            if(!action.matches("-?\\d+")){
                                System.out.println("Invalid number");
                                return;
                            }
                            int numPlayers = Integer.parseInt(action);
                            client.createGame(numPlayers);
                        } else {
                            view.printError("There's a time and place for everything, but not now. 2");
                        }
                        break;

                    case "join_game": //join_game>>gameID
                        if(!action.matches("-?\\d+")){
                            System.out.println("Invalid number");
                            return;
                        }
                        if (stage == TurnStages.CREATEorJOIN) {
                            int gameID = Integer.parseInt(action);
                            client.joinGame(gameID);
                        } else {
                            view.printError("There's a time and place for everything, but not now. 3");
                        }
                        break;



                    case "chat": //chat>>Write here your message...
                        if (stage == TurnStages.NOTURN || stage == TurnStages.TILESNUM || stage == TurnStages.CHOOSETILES || stage == TurnStages.CHOOSECOLUMN) {
                            String chatMessage = action;
                            chatMessage = lastUsedNickname + ": " + chatMessage;
                            view.printError("Sending...\n");
                            client.sendChatMessage(chatMessage);
                        }else {
                            view.printError("There's a time and place for everything, but not now. 3");
                        }
                        break;


                    case "num_tiles"://num_tiles>> number of tiles you want to get from the board
                        if(!action.matches("-?\\d+")){
                            System.out.println("Invalid number");
                            return;
                        }
                        if (stage == TurnStages.TILESNUM) {
                            int numTiles = Integer.parseInt(action);
                            client.tilesNumMessage(numTiles);
                        } else {
                            view.printError("There's a time and place for everything, but not now. 4");
                        }
                        break;

                    case "take_tiles": // tiles; format TBD (0,2);(1,3);(4,7)
                        if (stage == TurnStages.CHOOSETILES) {

                            if(!action.matches("^[\\d,;()]+$")){
                                System.out.println("Invalid tiles format");
                                return;
                            }

                            String chosenTiles = action;


                            //( 0 , 2 ) ; ( 1 , 3 )  ;  (  4  ,  7  )
                            //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
                            // -> 1,3 7,9 13,15

                            // tiles; format TBD (0,2);(1,3);(4,7) -> lunghezza <6-> 1 tile; <12->2tiles; else->3tiles
                            //client.getView(); (IMPORTANTE)
                            ArrayList<Tile> chosenTilesList = new ArrayList<>();
                            chosenTilesList.clear();
                            if(action.length() < 4){
                                System.out.println("Check the message format...\n");
                                return;
                            }
                            char[] chosen = chosenTiles.toCharArray();

                            if(!Character.isDigit(chosen[1]) || !Character.isDigit(chosen[3])){
                                System.out.println("Invalid tiles format");
                                return;
                            }

                            int row = Character.getNumericValue(chosen[1]);
                            int col = Character.getNumericValue(chosen[3]);
                            Tile t = new Tile(row, col, PossibleColors.valueOf(view.getTileColor(row, col)));
                            chosenTilesList.add(t);

                            if (chosenTiles.length() > 6) {
                                if(!Character.isDigit(chosen[7]) || !Character.isDigit(chosen[9])){
                                    System.out.println("Invalid tiles format");
                                    return;
                                }

                                row = Character.getNumericValue(chosen[7]);
                                col = Character.getNumericValue(chosen[9]);
                                Tile t2 = new Tile(row, col, PossibleColors.valueOf(view.getTileColor(row, col)));
                                chosenTilesList.add(t2);

                                if (chosenTiles.length() > 12) {
                                    if(!Character.isDigit(chosen[13]) || !Character.isDigit(chosen[15])){
                                        System.out.println("Invalid tiles format");
                                        return;
                                    }

                                    row = Character.getNumericValue(chosen[13]);
                                    col = Character.getNumericValue(chosen[15]);
                                    Tile t3 = new Tile(row, col, PossibleColors.valueOf(view.getTileColor(row, col)));
                                    chosenTilesList.add(t3);
                                }
                            }
                            for(Tile tile: chosenTilesList){
                                view.printTile(tile.getColor(), tile.getRow(), tile.getColumn());
                                view.printThings("; ");
                            }
                            view.printThings("\nWaiting for check...\n");
                            client.selectedTiles(chosenTilesList);
                        } else {
                            view.printError("There's a time and place for everything, but not now. 5");
                        }
                        break;


                    case "choose_column": //choose_column>>number of the column; TBD:le colonne partono da 0 o da 1????
                        if(!action.matches("-?\\d+")){
                            System.out.println("Invalid number");
                            return;
                        }
                        if (stage == TurnStages.CHOOSECOLUMN) {
                            int column = Integer.parseInt(action);
                            client.chooseColumn(column);
                        } else {
                            view.printError("There's a time and place for everything, but not now. 6");
                        }
                        break;


                    case "help":
                        view.printCommands();
                        break;


                    default:
                        System.out.println("Error! No valid message type detected!");
                }
            }else{
            System.out.println("You didn't write anything");
        }

    }


    /**
     * Setter method
     * @param connectionType is a String, either "RMI" or "TCP", used to know which type of message
     *                       has to be sent
     */
    public void setConnectionType(String connectionType, ViewInterface view) throws IOException{
        this.connectionType = connectionType;
        this.view = view;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/resources/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);
        if (connectionType.equalsIgnoreCase("TCP")){
            ClientTCP clientTCP = new ClientTCP(jsonObject.get("portTCP").getAsInt(),jsonObject.get("ip").getAsString(), view);
            clientTCP.setBuffer(this);
            Thread thread = new Thread(clientTCP);
            client = clientTCP;
            thread.start();
        }if(connectionType.equalsIgnoreCase("RMI")){
            ClientRMIApp clientRMI = new ClientRMIApp(jsonObject.get("portRMI").getAsInt(),jsonObject.get("ip").getAsString(), view);
            clientRMI.setBuffer(this);
            clientRMI.startClient();
            client = clientRMI;
        }

    }

    public void setStage(TurnStages stage){
        this.stage = stage;
    }

    public TurnStages getStage() {
        return stage;
    }
}
