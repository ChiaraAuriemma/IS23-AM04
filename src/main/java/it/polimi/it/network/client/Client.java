package it.polimi.it.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Choose a connection type");

        Scanner stdin = new Scanner(System.in);
        String inputLine = stdin.nextLine();
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/java/it/polimi/it/network/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);

        if(inputLine == "TCP"){
            ClientTCP clientTCP = new ClientTCP(jsonObject.get("portTCP").getAsInt(),jsonObject.get("ip").getAsString());
        }else if(inputLine == "RMI"){
            ClientRMI clientRMI = new ClientRMI(jsonObject.get("portRMI").getAsInt(),jsonObject.get("ip").getAsString());
        }
    }
}
