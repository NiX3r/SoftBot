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
                            results.getString("DiscordUserPing"));

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

    public static void loadPendingBazaarInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading pending bazaar into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Bazaar WHERE Status='PENDING'");

                ResultSet results = statement.executeQuery();

                while(results.next()){

                    BazaarInstance bazaar = new BazaarInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
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
                            results.getString("DiscordUserPing"));

                    Bot.getPendingData().getBazaar().add(bazaar);

                }
            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }


        }

        Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
        Utils.LogSystem.log(LogTypeEnum.INFO, "pending bazaar successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(true);

    }

}
