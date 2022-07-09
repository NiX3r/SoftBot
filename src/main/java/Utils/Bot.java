package Utils;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

public class Bot {

    private static String prefix = "SoftBot";
    private static DiscordApi bot;

    public static void initializeBot(boolean loadCache){

        bot = new DiscordApiBuilder().setToken(SecretClass.getDiscordToken()).setAllIntents().login().join();
        Utils.LogSystem.log(prefix, "bot is ready on : " + bot.createBotInvite(Permissions.fromBitmask(8)), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        if(loadCache){
            FileUtils.loadCache( cache -> {

                if(cache != null){
                    Utils.LogSystem.log(prefix, "cache successfully loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                    return;
                }

                Utils.LogSystem.log(prefix, "can't load cache. Initializing new instances..", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            });
        }
        initializeLogListeners();

        String status = "serving the server";
        bot.updateActivity(ActivityType.WATCHING, status);

        Utils.LogSystem.log(prefix, "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }

    public static DiscordApi getBot(){ return bot; }
    public static String getPrefix(){ return prefix; }

    public static void updateBotStatus(int memberCount){

        String status = memberCount + " members";
        bot.updateActivity(ActivityType.WATCHING, status);

    }

    private static void initializeLogListeners(){
        bot.addLostConnectionListener(listener -> {
            Utils.LogSystem.log(prefix, "lost connection", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
        bot.addReconnectListener(listener -> {
            Utils.LogSystem.log(prefix, "reconnecting", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
        bot.addResumeListener(listener -> {
            Utils.LogSystem.log(prefix, "resuming", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
    }
    
}
