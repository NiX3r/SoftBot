package Database;

import Enums.BanStatusEnum;
import Enums.BanTypeEnum;
import Enums.LogTypeEnum;
import Instances.BanInstance;
import Instances.CalendarGameInstance;
import Utils.Bot;
import Utils.UTFCorrectionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Consumer;

public class BanUtils {

    public static void loadBansInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading bans into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Ban WHERE Status='Ban'");

                ResultSet results = statement.executeQuery();

                while(results.next()){

                    String last_edit_date = results.getObject("LastEditDate", String.class);
                    BanInstance ban = new BanInstance(DatabaseUtils.decodeDiscordId(results.getString("DiscordUserID")),
                            DatabaseUtils.decodeDiscordId(results.getString("DiscordAdminID")),
                            UTFCorrectionTranslator.translate(results.getString("DiscordAdminNick")),
                            UTFCorrectionTranslator.translate(results.getString("Reason")),
                            BanStatusEnum.valueOf(results.getString("Status")),
                            BanTypeEnum.valueOf(results.getString("BanType")),
                            DatabaseUtils.decodeDiscordId(results.getString("LastEditAuthor")),
                            DatabaseUtils.decodeDateTime(results.getString("LastEditDate")),
                            BanStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            UTFCorrectionTranslator.translate(results.getString("LastEditReason")),
                            DatabaseUtils.decodeDateTime(results.getString("CreateDate")));

                    Bot.getPunishments().getBans().add(ban);

                }

                Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
                Utils.LogSystem.log(LogTypeEnum.INFO, "bans successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(true);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }


        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "ban not loaded. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

}
