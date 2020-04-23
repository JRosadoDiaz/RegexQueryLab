package edu.neumont.csc180.rosado.jose;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateCommand {

    /*
    Regex Groups:
    0: Full match
    1: Table name
    2: Column names
    3: Regex formats for respective columns
    4: File path

    Example Query:
    CREATE TABLE '<tableName>' (<csv format columnNames>) : line format /<regex pattern>/ file <filePath>

    CREATE TABLE 'people' (name, ssn, email, phone) : line format /([A-Za-z]*\s([A-Za-z0-9\.]*)?\s?[A-Z][A-Za-z]*) ([0-9]{3}-[0-9]{2}-[0-9]{4}) () ([0-9]{3}-[0-9]{3}-[0-9]{4})/ file 'people.to.regex.csv'
     */

    private static String createRegex = "^CREATE TABLE '([A-Za-z][A-Za-z0-9]*)'\\s?\\((.*,?)\\)\\s?:\\s?line format\\s?\\/(\\(.*\\)\\s?)*\\/\\s?file\\s?'(.*)'$";

    public static Table executeCommand(String command) throws FileNotFoundException {
        String tableName = "";
        String[] columnNames;
        String[] regexPattern;
        String filePath = "";
        Table newTable = null;

        Pattern p = Pattern.compile(createRegex);
        Matcher m = p.matcher(command);
        m.usePattern(p);

        while(m.find()){
            tableName = m.group(1);

            // Put the column name and regex pattern into a key value pair to read the file easier
            columnNames = m.group(2).toLowerCase().split(",");
            regexPattern = m.group(3).split(" ");

            Map<String, String> columnAndRegex = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                columnAndRegex.put(columnNames[i], regexPattern[i]);
            }

            filePath = m.group(4);

            File file = new File(m.group(4));
            if(!file.exists()) {
                System.out.println("File at path: '" + filePath + "' was not found within create query");
            }
            newTable = new Table(tableName, columnAndRegex, filePath);
        }


        // Return filepath
        return newTable;
    }


}
