package edu.neumont.csc180.rosado.jose;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectCommand {

    /*
    Regex Groups:
    0: Full match
    1: Column names
    2: Table name (Refer to table object in list)
    *** Optional ***
    3: WHERE Clause
    4: First operand
    5: Operator
    6: Second operand

    Example Query:
    SELECT <csv format column names> FROM <tableName> WHERE <criteria>

    SELECT name, ssn FROM people
     */

    private static String selectRegex = "^SELECT (.*,?) FROM ([A-Za-z][A-Za-z0-9.]*)\\s?(WHERE (.*) (<=|>=|=|<|>) (.*))?";

    public static void executeCommand(List<Table> tables,String command) {
        Table tableObj = null;
        Pattern p = Pattern.compile(selectRegex);
        Matcher m = p.matcher(command);
        m.usePattern(p);

        String[] queryColumns = null;
        while(m.find()){
            for(Table t : tables) { // Find table from list
                queryColumns = m.group(1).toLowerCase().split(",");
                if(m.group(2).equals(t.getTableName())) {
                    tableObj = t;
                }
            }
        }

        if(tableObj == null) {
            System.out.println("Could not find table name '" + m.group(2) + "'");
        }
        else {
            // Gather content from file
            List<String> content = readFile(tableObj.getFilePath());

            // Print results
            displayData(content, tableObj, queryColumns);
        }
    }

    private static List<String> readFile(String filePath) {
        List<String> list = new ArrayList<>();
        try{
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String data = reader.nextLine();

                list.add(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: the file was not found");
            e.printStackTrace();
        }

        return list;
    }

    private static void displayData(List<String> content, Table tableObj, String[] queryColumns) {
        // for each line, use the table object's map to find the data needed for the columns requested

        // Prepare headers for table view
        String headerString = "";
        for (String h : queryColumns) {
            headerString += String.format("%-25s", h);
        }
        System.out.println(headerString);

        for (int i = 0; i < queryColumns.length * 25; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Prepare data lines for table view
        for(String line : content) {
            String formattedData = "";
            for (String column : queryColumns) { // for each column in query, use its assigned regex to add to the data string
                String regex = tableObj.getColumnAndRegexPairList().get(column);
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(line);
                m.usePattern(p);

                while(m.find()) {
                    formattedData += String.format("%-25s", m.group(1));
                }
            }
            System.out.println(formattedData); // print data
        }
    }
}
