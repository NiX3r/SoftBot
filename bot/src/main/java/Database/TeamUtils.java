package Database;

import Enums.LogTypeEnum;
import Enums.TeamStatusEnum;
import Instances.CalendarGameInstance;
import Instances.TeamInstance;
import Utils.Bot;
import Utils.UTFCorrectionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Consumer;

public class TeamUtils {

    public static void loadTeamsInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading teams into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Team WHERE Status='APPROVED'");

                ResultSet results = statement.executeQuery();

                while(results.next()){

                    String last_edit_date = results.getObject("LastEditDate", String.class);
                    TeamInstance mainInstance = new TeamInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("IPAddress"),
                            results.getString("Thumbnail"),
                            results.getString("Website"),
                            UTFCorrectionTranslator.translate(results.getString("Type")),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordServerID")),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            TeamStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDiscordId(results.getString("LastEditAuthor")),
                            (last_edit_date == null ? 0 : DatabaseUtils.decodeDateTime(last_edit_date)),
                            TeamStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            DatabaseUtils.decodeDateTime(results.getObject("CreateDate", String.class)),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));

                    Bot.getTeamUtil().getTeams().add(mainInstance);

                }

                Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
                Utils.LogSystem.log(LogTypeEnum.INFO, "teams successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(true);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "teams not loaded. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

    public static void getLastestPendingTeam(Consumer<TeamInstance> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "trying to get lastest pending team", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Team WHERE Status='PENDING' ORDER BY CreateDate ASC LIMIT 1");

                ResultSet results = statement.executeQuery();
                TeamInstance mainInstance = null;
                if(results.next()){
                    String last_edit_date = results.getObject("LastEditDate", String.class);
                    mainInstance = new TeamInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("IPAddress"),
                            results.getString("Thumbnail"),
                            results.getString("Website"),
                            UTFCorrectionTranslator.translate(results.getString("Type")),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordServerID")),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            TeamStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDiscordId(results.getString("LastEditAuthor")),
                            (last_edit_date == null ? 0 : DatabaseUtils.decodeDateTime(last_edit_date)),
                            TeamStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            DatabaseUtils.decodeDateTime(results.getObject("CreateDate", String.class)),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));
                }

                Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
                Utils.LogSystem.log(LogTypeEnum.INFO, "successfully get latest pending team", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(mainInstance);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(null);
                return;
            }

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "can't get team. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(null);
    }

    public static void updateTeamStatus(int id, TeamStatusEnum status, Consumer<Boolean> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "updating team with id: " + id, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("UPDATE Team SET Status=? WHERE ID=?");
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

        Utils.LogSystem.log(LogTypeEnum.ERROR, "team not updated. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

}
