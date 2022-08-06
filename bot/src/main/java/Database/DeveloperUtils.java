package Database;

import Enums.GameStatusEnum;
import Enums.LogTypeEnum;
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

public class DeveloperUtils {

    public static void loadAdmins(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading admins into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

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
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while parsing. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }
            catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }


        }

        Utils.LogSystem.log(LogTypeEnum.INFO, "admins successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(true);

    }

    public static void addAdmin(AdminInstance admin, Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "adding new admin '" + admin.getNick() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("INSERT INTO Admin(Nick,DiscordID,CreateDate) VALUES(?,?,?)");
                statement.setString(1, admin.getNick());
                statement.setString(2, DatabaseUtils.encodeDiscordId(admin.getDiscord_id()));
                statement.setString(3, DatabaseUtils.encodeDateTime(admin.getCreate_date()));

                boolean success = !statement.execute();

                if(success){
                    callback.accept(true);
                }
                else {
                    callback.accept(false);
                    return;
                }

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }

            Utils.LogSystem.log(LogTypeEnum.INFO, "admin '" + admin.getNick() + "' successfully added", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            return;

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "admin '" + admin.getNick() + "' cannot be added - database is not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

    public static void removeAdmin(long discord_id, Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "removing  admin by Discord ID '" + discord_id + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("DELETE FROM Admin WHERE DiscordID=?");
                statement.setString(1, DatabaseUtils.encodeDiscordId(discord_id));

                boolean success = !statement.execute();

                if(success){
                    callback.accept(true);
                }
                else {
                    callback.accept(false);
                    return;
                }

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }

            Utils.LogSystem.log(LogTypeEnum.INFO, "admin '" + discord_id + "' successfully removed", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            return;

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "admin '" + discord_id + "' cannot be removed - database is not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

}
