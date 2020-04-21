package edu.neumont.csc180.rosado.jose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUI {

    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private String createRegex = "^CREATE TABLE '([A-Za-z][A-Za-z0-9]*)'\\s?\\((.*,?)\\)\\s?:\\s?line format\\s?\\/(\\(.*\\)\\s?)*\\/\\s?file\\s?'(.*)'$";
    private String selectRegex = "^SELECT (.*) FROM ([A-Z][A-Za-z0-9]*) WHERE (.*)\\s(<=|>=|<|>|=)\\s(.*)$";
    String p = "";
    public boolean loopSwitch = true;

    public void start(){
        initialSetup();

        System.out.println("Welcome to ReQL! input h or help for more instructions");
        System.out.println("Input command: ");
        do {
            try {
                System.out.print("> ");
                String command = in.readLine();
                processCommand(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while((loopSwitch));
    }

    /// This will create the necessary folder for
    private void initialSetup() {
    }

    public boolean processCommand(String command) {
        if(command.matches(createRegex)){

        }
        else if(command.matches(selectRegex)){

        }
        else{
            System.out.println("Could not recognize command, try again");
            return false;
        }

        return true;
    }
}
