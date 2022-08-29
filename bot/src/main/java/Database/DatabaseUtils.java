package Database;

import Enums.LogTypeEnum;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class DatabaseUtils {

    public static void loadDataIntoCache(Consumer<Boolean> callback){
        TeamUtils.loadPendingTeamsInstance(load_pending_team_success -> {
            if(load_pending_team_success){
                Database.GameUtils.loadPendingCalendarInstance(load_pending_game_success -> {
                    if (load_pending_game_success){
                        BazaarUtils.loadPendingBazaarInstance(load_pending_bazaar_success -> {
                            if (load_pending_bazaar_success){
                                BazaarUtils.loadBazaarInstance(load_bazaar_success -> {
                                    if(load_bazaar_success){
                                        ServerOptionUtils.loadServerOptionInstance(load_server_option_success -> {
                                            if(load_server_option_success){
                                                BanUtils.loadBansInstance(load_bans_success -> {
                                                    if(load_bans_success){
                                                        TeamUtils.loadTeamsInstance(load_teams_success -> {
                                                            if(load_teams_success){
                                                                DeveloperUtils.loadAdmins(load_admins_success -> {
                                                                    if(load_admins_success){
                                                                        Database.GameUtils.loadCalendarInstance(load_calendar_success -> {
                                                                            if(load_calendar_success){
                                                                                ShopUtils.loadShopsInstance(load_shop_success -> {
                                                                                    if(load_shop_success){
                                                                                        ShopUtils.loadPendingShopsInstance(load_pending_shop_success -> {
                                                                                            if (load_pending_shop_success){
                                                                                                callback.accept(true);
                                                                                            }
                                                                                            else {
                                                                                                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading pending shops. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                    else {
                                                                                        Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading shops. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                                                                    }
                                                                                });
                                                                            }
                                                                            else {
                                                                                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading calendar. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                                                            }
                                                                        });
                                                                    }
                                                                    else {
                                                                        Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading admins. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                                                    }
                                                                });
                                                            }
                                                            else {
                                                                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading teams. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                                            }
                                                        });
                                                    }
                                                    else {
                                                        Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading bans. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                                    }
                                                });
                                            }
                                            else {
                                                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading server options. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                            }
                                        });
                                    }
                                    else {
                                        Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading bazaar. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                    }
                                });
                            }
                            else {
                                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading pending bazaar. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                            }
                        });
                    }
                    else {
                        Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading pending game. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                    }
                });
            }
            else {
                Utils.LogSystem.log(LogTypeEnum.ERROR, "error while loading pending team. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            }
        });
    }

    public static long decodeDateTime(String dateTime){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date end_date = sdf.parse(dateTime);
            return end_date.getTime();
        } catch (ParseException e) {
        }

        return -1;

    }

    public static String encodeDateTime(long dateTime){

        DateFormat obj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date res = new Date(dateTime);

        return obj.format(res);

    }

    public static long decodeDiscordId(String id){

        try{
            return Long.parseLong(id);
        }
        catch (Exception ex){
            return -1;
        }

    }

    public static String encodeDiscordId(long id){

        if(id == -1)
            return "";
        else
            return String.valueOf(id);

    }

}
