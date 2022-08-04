package Database;

import Enums.GameStatusEnum;
import Instances.AdminInstance;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import Utils.UTFCorrectionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Consumer;

public class DeveloperCommandUtils {

    public static void loadAdmins(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log("PROGRAM", "loading admins into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Admin;");

                ResultSet results = statement.executeQuery();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                while(results.next()){

                    Date create_date = sdf.parse(results.getObject("CreateDate", String.class));
                    long create_ms = create_date.getTime();
                    AdminInstance admin = new AdminInstance(
                            results.getString("Nick"),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordId")),
                            create_ms
                    );

                    Bot.getAdmins().add(admin);

                }
            }catch (ParseException e) {
                Utils.LogSystem.log("PROGRAM", "error while parsing. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }
            catch (SQLException e) {
                Utils.LogSystem.log("PROGRAM", "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }


        }

        Utils.LogSystem.log("PROGRAM", "admins successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(true);

    }

    public static void addAdmin(AdminInstance admin, Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log("PROGRAM", "adding new admin '" + admin.getNick() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("INSERT INTO Admin(Nick,DiscordID,CreateDate) VALUES(?,?,?)");
                statement.setString(1, admin.getNick());
                statement.setString(2, DatabaseUtils.encodeDiscordId(admin.getDiscord_id()));
                statement.setString(3, DatabaseUtils.encodeDateTime(admin.getCreate_date()));

                System.out.println(statement.toString());

                boolean success = !statement.execute();

                if(success){
                    callback.accept(true);
                    return;
                }
                else {
                    callback.accept(false);
                    return;
                }

            }catch (SQLException e) {
                Utils.LogSystem.log("PROGRAM", "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }


        }

        Utils.LogSystem.log("PROGRAM", "admin '" + admin.getNick() + "' successfully added", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(true);

    }

}
