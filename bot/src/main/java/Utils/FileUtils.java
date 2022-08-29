package Utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.javacord.api.entity.Attachment;
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

    public static void saveAttachments(Attachment attachment, String objectToSave, int id, Consumer<Boolean> callback){

        String key;
        try {

            byte[] file = attachment.downloadAsByteArray().get();
            key = attachment.getFileName().substring(attachment.getFileName().indexOf("."));
            String path = "./data/attachments/" + objectToSave + "/" + id;

            new File(path).mkdirs();

            try (FileOutputStream fos = new FileOutputStream(path + "/file" + key)) {
                fos.write(file);
                fos.flush();
            }
            callback.accept(true);

        } catch (InterruptedException e) {
            e.printStackTrace();
            callback.accept(false);
        } catch (ExecutionException e) {
            e.printStackTrace();
            callback.accept(false);
        } catch (FileNotFoundException e) {
            callback.accept(false);
            throw new RuntimeException(e);
        } catch (IOException e) {
            callback.accept(false);
            throw new RuntimeException(e);
        }

    }

    public static void loadAttachments(int id, String objectToLoad, Consumer<ArrayList<File>> callback){

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
