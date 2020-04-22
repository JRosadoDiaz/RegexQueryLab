package edu.neumont.csc180.rosado.jose;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CreateCommand {

    /*
    Regex Groups:
    0: Full match
    1: Table name
    2: Column names
    3: Regex formats for respective columns
    4: File path
     */
    private static String createRegex = "^CREATE TABLE '([A-Za-z][A-Za-z0-9]*)'\\s?\\((.*,?)\\)\\s?:\\s?line format\\s?\\/(\\(.*\\)\\s?)*\\/\\s?file\\s?'(.*)'$";

    public static String executeCommand(String command){
        String filePath = null;
        File file;

        Pattern p = Pattern.compile(createRegex);
        Matcher m = p.matcher(command);
        m.usePattern(p);

        while(m.find()){
            filePath = m.group(4);
            file = new File(filePath);
            if(!file.exists()){
                System.out.println("File created at " + filePath);
            }

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(m.group(1) + "\n");
                writer.write(m.group(2) + "\n");
                writer.write(m.group(3) + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Return filepath
        return filePath;
    }


}
