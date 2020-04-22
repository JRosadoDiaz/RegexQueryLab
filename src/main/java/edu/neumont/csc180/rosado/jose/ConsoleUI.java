package edu.neumont.csc180.rosado.jose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUI {

    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private String selectRegex = "^SELECT (.*) FROM ([A-Z][A-Za-z0-9]*) WHERE (.*)\\s(<=|>=|<|>|=)\\s(.*)$";
    private String filePath;

    public boolean loopSwitch = true;

    public void start() {
        System.out.println("Welcome to ReQL! input h or help for more instructions\n" +
                "Type 'exit' to close");
        System.out.println("Input command: ");
        do {
            try {
                System.out.print("> ");
                String command = in.readLine();
                processCommand(command);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } while((loopSwitch));
    }

    public void processCommand(String command) {
        if(command.contains("CREATE TABLE")){
            filePath = CreateCommand.executeCommand(command);
        }
        else if(command.contains("SELECT")){
//            if(filePath == null){
//                throw new IllegalArgumentException("No file currently selected. Please create one via a CREATE statement and try again");
//            }
            SelectCommand.executeCommand(command);
        }
        else if(command.equals("h") || command.equals("help")){
            System.out.println(
                    "ReQL is a data structure that creates text database\n" +
                            "files based on a regex schema you create\n" +
                            "\nCreate command:\n" +
                            "CREATE TABLE '<table name>' (<csv format columns>)\n" +
                            ": line format /<regex group for each column>/ file '<file path>'\n" +
                            "\nSelect command:\n" +
                            "SELECT <csv format column names> FROM <table name>\n" +
                            "WHERE <criteria>\n" +
                            "AND <criteria> *optional*"
            );
        }
        else if(command.equals("exit")) {
            System.exit(0);
        }
        else{
            System.out.println("Could not recognize command, try again");
        }
    }
}
