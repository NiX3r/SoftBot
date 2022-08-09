package Database;

import Enums.LogTypeEnum;
import Enums.TeamStatusEnum;
import Instances.CalendarGameInstance;
import Instances.ServerOptionInstance;
import Instances.TeamInstance;
import Utils.Bot;
import Utils.UTFCorrectionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Consumer;

public class ServerOptionUtils {

    public static void loadServerOptionInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading server options into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM ServerOption");

                ResultSet results = statement.executeQuery();

                while(results.next()){

                    ServerOptionInstance serverOption = new ServerOptionInstance(DatabaseUtils.decodeDiscordId(results.getString("DiscordServerID")),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordAnnouncementChannelID")),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordTeamMemberRoleID")));

                    Bot.getServerOptions().add(serverOption);

                }

                Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
                Utils.LogSystem.log(LogTypeEnum.INFO, "server options successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(true);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "server options not loaded. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

    public static void addServerOption(long server_id, long channel_id, long member_role_id, Consumer<Boolean> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "adding new instance of server option '" + server_id + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("INSERT INTO ServerOption(DiscordServerID,DiscordAnnouncementChannelID,DiscordTeamMemberRoleID) VALUES(?,?,?)");
                statement.setString(1, DatabaseUtils.encodeDiscordId(server_id));
                statement.setString(2, DatabaseUtils.encodeDiscordId(channel_id));
                statement.setString(3, DatabaseUtils.encodeDiscordId(member_role_id));

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

            Utils.LogSystem.log(LogTypeEnum.INFO, "server option successfully added", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            return;

        }

        Utils.LogSystem.log(LogTypeEnum.INFO, "server option cannot be added - database is not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

    public static void editChannel(long server_id, long channel_id, Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "editing channel id for server '" + server_id + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("UPDATE ServerOption SET DiscordAnnouncementChannelID=? WHERE DiscordServerID=?");
                statement.setString(1, DatabaseUtils.encodeDiscordId(channel_id));
                statement.setString(2, DatabaseUtils.encodeDiscordId(server_id));

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

            Utils.LogSystem.log(LogTypeEnum.INFO, "announcement channel successfully edited", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            return;

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "announcement channel cannot be edited - database is not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

    public static void editMemberRole(long server_id, long member_role_id, Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "editing team member role id for server '" + server_id + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("UPDATE ServerOption SET DiscordTeamMemberRoleID=? WHERE DiscordServerID=?");
                statement.setString(1, DatabaseUtils.encodeDiscordId(member_role_id));
                statement.setString(2, DatabaseUtils.encodeDiscordId(server_id));

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

            Utils.LogSystem.log(LogTypeEnum.INFO, "team member role id successfully edited", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            return;

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "team member role id cannot be edited - database is not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

}
