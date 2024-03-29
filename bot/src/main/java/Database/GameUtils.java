package Database;

import Enums.GameStatusEnum;
import Enums.LogTypeEnum;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Utils.Bot;
import Utils.UTFCorrectionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Consumer;

public class GameUtils {

    public static void loadCalendarInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading calendar into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Game WHERE Status='APPROVED'");

                ResultSet results = statement.executeQuery();

                while(results.next()){

                    String last_edit_date = results.getObject("LastEditDate", String.class);
                    GameInstance mainInstance = new GameInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("Thumbnail"),
                            results.getString("IPAddress"),
                            DatabaseUtils.decodeDateTime(results.getObject("StartDate", String.class)),
                            DatabaseUtils.decodeDateTime(results.getObject("EndDate", String.class)),
                            results.getString("RepeatDate"),
                            results.getString("Website"),
                            UTFCorrectionTranslator.translate(results.getString("Location")),
                            results.getFloat("Price"),
                            UTFCorrectionTranslator.translate(results.getString("Type")),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            GameStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDiscordId(results.getString("LastEditAuthor")),
                            (last_edit_date == null ? 0 : DatabaseUtils.decodeDateTime(last_edit_date)),
                            GameStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            DatabaseUtils.decodeDateTime(results.getObject("CreateDate", String.class)),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));

                    Bot.getCalendar().getGames().add(mainInstance);
                    Bot.getCalendar().addCalendarGame(mainInstance);

                }

                Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
                Utils.LogSystem.log(LogTypeEnum.INFO, "calendar successfully initialized, loaded and sorted", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(true);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }

        }
        Utils.LogSystem.log(LogTypeEnum.ERROR, "pending calendar not loaded. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

    public static void getLastestPendingGame(Consumer<GameInstance> callback){
        if(!Bot.getDatabaseConnection().isClosed()){
            Utils.LogSystem.log(LogTypeEnum.INFO, "trying to get lastest pending game", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Game WHERE Status='PENDING' ORDER BY CreateDate ASC LIMIT 1");

                ResultSet results = statement.executeQuery();
                GameInstance mainInstance = null;
                if(results.next()){
                    String last_edit_date = results.getObject("LastEditDate", String.class);
                     mainInstance = new GameInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("Thumbnail"),
                            results.getString("IPAddress"),
                            DatabaseUtils.decodeDateTime(results.getObject("StartDate", String.class)),
                            DatabaseUtils.decodeDateTime(results.getObject("EndDate", String.class)),
                            results.getString("RepeatDate"),
                            results.getString("Website"),
                            UTFCorrectionTranslator.translate(results.getString("Location")),
                            results.getFloat("Price"),
                            UTFCorrectionTranslator.translate(results.getString("Type")),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            GameStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDiscordId(results.getString("LastEditAuthor")),
                            (last_edit_date == null ? 0 : DatabaseUtils.decodeDateTime(last_edit_date)),
                            GameStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            DatabaseUtils.decodeDateTime(results.getObject("CreateDate", String.class)),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));
                }

                Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
                Utils.LogSystem.log(LogTypeEnum.INFO, "successfully get latest pending calendar", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(mainInstance);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(null);
                return;
            }
        }
        Utils.LogSystem.log(LogTypeEnum.ERROR, "can't get game. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(null);
    }

    public static void updateGameStatus(int id, GameStatusEnum status, Consumer<Boolean> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "updating game with id: " + id, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("UPDATE Game SET Status=? WHERE ID=?");
                statement.setString(1, status.toString());
                statement.setInt(2, id);

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
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "game not updated. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

}
