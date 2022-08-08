package Utils;

import Enums.LogTypeEnum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class LogSystem {

    private static List<String> logs;

    public static void log(LogTypeEnum type, String content, int lineNo, String fileName, String methodName){

        if(logs == null)
            logs = new ArrayList<String>();

        String color = "";

        switch (type){
            case ERROR:
                color = (char)27 + "[31m";
                break;
            case WARNING:
                color = (char)27 + "[33m";
                break;
            case INFO:
                color = (char)27 + "[0m";
                break;
        }

        String line = color + "[" + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)) + "] (" + type.toString() + ") " + fileName + " : " + methodName + "(" + lineNo + ") >> " + content;

        logs.add(line);
        System.out.println(line);

        if(logs.size() == 50)
            saveLogs();

    }

    public static void saveLogs(){

        FileWriter fw = null;
        new File("./data").mkdir();
        try {
            fw = new FileWriter("./data/log.log", true);
            BufferedWriter bw = new BufferedWriter(fw);

            for(String line : logs){

                bw.write(line);
                bw.newLine();

            }

            bw.flush();
            bw.close();
            logs.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
