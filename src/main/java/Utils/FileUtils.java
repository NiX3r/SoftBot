package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class FileUtils {

    public static void saveCache(Consumer<Exception> consumer){

        String path = "./data";
        new File(path).mkdirs();

        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    path + "/cache.json"));
            String json = new GsonBuilder().setPrettyPrinting().create().toJson("add");
            f_writer.write(json);
            f_writer.flush();
            f_writer.close();
            Utils.LogSystem.log(Utils.Bot.getPrefix(), "cache saved", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(null);
        } catch (Exception e){
            Utils.LogSystem.log(Utils.Bot.getPrefix(), "can't save cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(e);
        }

    }

    public static void loadCache(Consumer<ArrayList<Integer> /* temp output */> consumer){

        String path = "./data/cache.json";

        try{
            String json = new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e){
            Utils.LogSystem.log(Utils.Bot.getPrefix(), "can't load cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(null);
        }

    }

}
