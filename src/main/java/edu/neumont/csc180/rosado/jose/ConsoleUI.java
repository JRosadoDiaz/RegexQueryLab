package edu.neumont.csc180.rosado.jose;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {

    /*
    Order of operations
    1. Create statement
        * The statement creates a table configuration based on table name, column names, and regex pattern
        * file path points to an already created file with established data
     */

    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private List<Table> tables = new ArrayList<>();

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
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        } while((loopSwitch));
    }

    public void processCommand(String command) {
        try{
            if(command.contains("CREATE TABLE")){
                System.out.println("Creating table...");
                Table newTable = CreateCommand.executeCommand(command);

                if(newTable != null) {
                    System.out.println("Table " + newTable.getTableName() + " successfully created");
                    tables.add(newTable);
                }
                else {
                    System.out.println("There was no match or the statement was structured incorrectly");
                }

            }
            else if(command.contains("SELECT")){
                SelectCommand.executeCommand(tables, command);
            }
            else if(command.equals("h") || command.equals("help")){
                System.out.println(
                        "ReQL is a data query system that loads data from a file\n" +
                                "to create tables based on regex its given\n" +
                                "\nCreate command:\n" +
                                "CREATE TABLE '<table name>' (<csv format columns>)\n" +
                                ": line format /<regex group for each column>/ file '<file path>'\n" +
                                "\nSelect command:\n" +
                                "SELECT <csv format column names> FROM <table name>\n" +
                                "WHERE <criteria> * Optional *"
                );
            }
            else if(command.equals("exit")) {
                System.exit(0);
            }
            else{
                System.out.println("Could not recognize command, try again");
            }
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
