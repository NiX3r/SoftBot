package Database;

import Enums.LogTypeEnum;
import Enums.ShopStatusEnum;
import Instances.CalendarGameInstance;
import Instances.ShopInstance;
import Utils.Bot;
import Utils.UTFCorrectionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Consumer;

public class ShopUtils {

    public static void loadShopsInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "loading shops into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Shop WHERE Status='APPROVED'");

                ResultSet results = statement.executeQuery();

                while(results.next()){

                    String last_edit_date = results.getObject("LastEditDate", String.class);
                    ShopInstance mainInstance = new ShopInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("Voucher"),
                            results.getString("IPAddress"),
                            "-1",
                            results.getString("Website"),
                            results.getString("Location"),
                            results.getInt("ZIP"),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            ShopStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDateTime(results.getObject("CreateDate", String.class)),
                            DatabaseUtils.decodeDiscordId(results.getString("LastEditAuthor")),
                            (last_edit_date == null ? 0 : DatabaseUtils.decodeDateTime(last_edit_date)),
                            ShopStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            results.getString("Thumbnail"),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));

                    Bot.getShop().getShops().add(mainInstance);

                }

                Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
                Utils.LogSystem.log(LogTypeEnum.INFO, "shops successfully initialized and loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(true);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
                return;
            }

        }

        Utils.LogSystem.log(LogTypeEnum.ERROR, "shops not loaded. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

    public static void getLatestPendingShop(Consumer<ShopInstance> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "trying to get latest pending shop", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Shop WHERE Status='PENDING' ORDER BY CreateDate ASC LIMIT 1");

                ResultSet results = statement.executeQuery();
                ShopInstance mainInstance = null;
                if(results.next()){
                    String last_edit_date = results.getObject("LastEditDate", String.class);
                     mainInstance = new ShopInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("Voucher"),
                            results.getString("IPAddress"),
                            "-1",
                            results.getString("Website"),
                            results.getString("Location"),
                            results.getInt("ZIP"),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            ShopStatusEnum.valueOf(results.getString("Status")),
                            DatabaseUtils.decodeDateTime(results.getObject("CreateDate", String.class)),
                            DatabaseUtils.decodeDiscordId(results.getString("LastEditAuthor")),
                            (last_edit_date == null ? 0 : DatabaseUtils.decodeDateTime(last_edit_date)),
                            ShopStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            results.getString("Thumbnail"),
                            DatabaseUtils.decodeDiscordId(results.getObject("DiscordUserID", String.class)));
                }
                Utils.LogSystem.log(LogTypeEnum.INFO, "successfully get latest pending shop", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(mainInstance);
                return;

            }catch (SQLException e) {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(null);
                return;
            }

        }
        Utils.LogSystem.log(LogTypeEnum.ERROR, "can't get shop. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(null);
    }

    public static void updateShopStatus(int id, ShopStatusEnum status, Consumer<Boolean> callback){
        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log(LogTypeEnum.INFO, "updating shop with id: " + id, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("UPDATE Shop SET Status=? WHERE ID=?");
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

        Utils.LogSystem.log(LogTypeEnum.ERROR, "shop not updated. Database not connected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(false);

    }

}
