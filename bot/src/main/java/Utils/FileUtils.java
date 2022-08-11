package Utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.javacord.api.entity.message.MessageAttachment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class FileUtils {

    public static void saveAttachments(List<MessageAttachment> attachments, String objectToSave, int id, Consumer<Boolean> callback){

        for (MessageAttachment attachment : attachments){

            String key, fileName;
            try {

                byte[] file = attachment.downloadAsByteArray().get();
                System.out.println(file.length);
                fileName = attachment.getFileName();
                key = fileName.substring(0, fileName.indexOf("."));
                String path = "./data/attachments/" + objectToSave + "/" + id;

                new File(path).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(path + "/" + fileName)) {
                    fos.write(file);
                    fos.flush();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public static void loadFiles(int id, String objectToLoad, Consumer<ArrayList<File>> callback){

        String path = "./data/attachments/" + objectToLoad + "/" + id;
        File dir = new File(path);
        ArrayList<File> output = new ArrayList<File>();

        File[] files = dir.listFiles();

        for(File file : files){

            output.add(file);

        }

        callback.accept(output);

    }

}
