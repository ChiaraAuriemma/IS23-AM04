package it.polimi.it.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        return;
    }
}
