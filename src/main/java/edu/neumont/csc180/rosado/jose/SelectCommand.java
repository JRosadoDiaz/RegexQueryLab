package edu.neumont.csc180.rosado.jose;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SelectCommand {
    private static String selectRegex = "^SELECT (.*,?) FROM ([A-Za-z][A-Za-z0-9.]*) (WHERE (.*) (<=|>=|=|<|>) (.*))?";

    public static void executeCommand(String command) {
        String filePath = null;
        File file;

        Pattern p = Pattern.compile(selectRegex);
        Matcher m = p.matcher(command);
        m.usePattern(p);

        // Find file with table
        while(m.find()){
            filePath = m.group(2);
            file = new File(filePath);
            if(file.exists()){
                readFile(filePath);
            }
        }
    }

    private static String tableName = "";
    private static String columnNames = "";
    private static String regexPatterns = "";

    private static void readFile(String filePath) {
        List<String> content = new ArrayList<>();
        try{
            File file = new File(filePath);
            Scanner reader = new Scanner(file);

            int count = 3; // count that helps collect first three lines
            while(reader.hasNextLine()){
                String data = reader.nextLine();

                switch (count){
                    case 3:
                        tableName = data;
                        count--;
                        break;
                    case 2:
                        columnNames = data;
                        count--;
                        break;
                    case 1:
                        regexPatterns = data;
                        count--;
                        break;
                    case 0:
                        content.add(data);
                        break;
                }
                System.out.println(data);
            }
            reader.close();

            displayData(content);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    private static void displayData(List<String> content) {
        String[] header = columnNames.split(",");
        String headerString = "";
        for (String h : header) {
            headerString += String.format("%-25s", h);
        }
        System.out.println(headerString);
        for (int i = 0; i < header.length * 25; i++) {
            System.out.print("-");
        }
        System.out.println();

        for(String a : content) {
            String formattedData = "";
            String[] data = a.split(",");
            for(String item : data){
                formattedData += String.format("%-25s", item);
            }
            System.out.println(formattedData);
        }
    }
}
