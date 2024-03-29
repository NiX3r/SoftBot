package Database;

import Enums.BazaarStatusEnum;
import Enums.BazaarTypeEnum;
import Enums.LogTypeEnum;
import Instances.BazaarInstance;
import Instances.CalendarGameInstance;
import Utils.Bot;
import Utils.UTFCorrectionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Consumer;

public class BazaarUtils {

    public static void loadBazaarInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading bazaar into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Bazaar WHERE Status='APPROVED'");

                ResultSet results = statement.executeQuery();

                while(results.next()){

                    BazaarInstance bazaar = new BazaarInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("IPAddress"),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordUserID")),
                            BazaarTypeEnum.valueOf(results.getString("Type")),
                            results.getInt("ZIP"),
                            results.getDouble("Price"),
                            results.getString("ImageDirectory"),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            BazaarStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDateTime("CreateDate"),
                            DatabaseUtils.decodeDiscordId("LastEditAuthor"),
                            DatabaseUtils.decodeDateTime("LastEditDate"),
                            results.getString("LastEditStatus") == null ? BazaarStatusEnum.NULL : BazaarStatusEnum.valueOf("LastEditAuthor"),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));

                    Bot.getBazaar().getBazaar().add(bazaar);

                }
            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }


        }

        Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
        Utils.LogSystem.log(LogTypeEnum.INFO, "bazaar successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(true);

    }

    public static void getLastestPendingBazaar(Consumer<BazaarInstance> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading bazaar into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Bazaar WHERE Status='PENDING' ORDER BY CreateDate ASC LIMIT 1");

                ResultSet results = statement.executeQuery();
                BazaarInstance bazaar = null;
                if(results.next()){
                     bazaar = new BazaarInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("IPAddress"),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordUserID")),
                            BazaarTypeEnum.valueOf(results.getString("Type")),
                            results.getInt("ZIP"),
                            results.getDouble("Price"),
                            results.getString("ImageDirectory"),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            BazaarStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDateTime("CreateDate"),
                            DatabaseUtils.decodeDiscordId("LastEditAuthor"),
                            DatabaseUtils.decodeDateTime("LastEditDate"),
                            results.getString("LastEditStatus") == null ? BazaarStatusEnum.NULL : BazaarStatusEnum.valueOf("LastEditAuthor"),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));
                }
                Utils.LogSystem.log(LogTypeEnum.INFO, "successfully get latest pending calendar", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(bazaar);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(null);
                return;
            }
        }
        Utils.LogSystem.log(LogTypeEnum.INFO, "can't get bazaar. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(null);
    }

    public static void updateBazaarStatus(int id, BazaarStatusEnum status, Consumer<Boolean> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "updating bazaar with id: " + id, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("UPDATE Bazaar SET Status=? WHERE ID=?");
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

        Utils.LogSystem.log(LogTypeEnum.ERROR, "bazaar not updated. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

}
