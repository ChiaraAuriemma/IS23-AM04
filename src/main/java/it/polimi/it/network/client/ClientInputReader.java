package it.polimi.it.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientInputReader implements Runnable{
    private volatile boolean running;
    @Override
    public void run() {
        running = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (running) {
            try {
                String input = reader.readLine();

                commandParser(input);
                if (input.equalsIgnoreCase("exit")) {
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

    public void commandParser(String input){
        String regex = "(\\w+)>>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String command = matcher.group(1);
            command = command.toLowerCase();


            switch (command) {
                case "login": //initial nickname setting
                    String nickname = input.substring(matcher.end());
                    ;break;

                case "create_game":// create_game>>4
                    int numPlayers = Integer.parseInt(input.substring(matcher.end()));
                    ;break;

                case "join_game": //join_game>>gameID
                    int gameID = Integer.parseInt(input.substring(matcher.end()));
                    ;break;

                case "chat": //chat>>Write here your message...
                    String chatMessage = input.substring(matcher.end());
                    ;break;

                case "connection": //connection>>RMI     or TCP
                    String connectionType = input.substring(matcher.end());
                    ;break;

                case "num_tiles": //num_tiles>> number of tiles you want to get from the board
                    int numTiles = Integer.parseInt(input.substring(matcher.end()));
                    ;break;

                case "take_tiles": // tiles; format TBD
                    String chosenTiles = input.substring(matcher.end());
                    ;break;

                case "choose_column": //choose_column>>number of the column; TBD:le colonne partono da 0 o da 1????
                    int column = Integer.parseInt(input.substring(matcher.end()));
                    ;break;

                case "order": //reorders the tiles; TBD il formato: tipo 1-3-2 per riordinare le tiles prima->terza->seconda nella lista
                    String order = input.substring(matcher.end());
                    ;break;


                default : System.out.println("Error! No valid message type detected!");
            }
        }
    }
}
