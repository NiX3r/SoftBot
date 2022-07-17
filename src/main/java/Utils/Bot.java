package Utils;

import Database.DatabaseConnection;
import Listeners.nMessageCreateListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.permission.Permissions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bot {

    private static String prefix = "§ SoftBot §";
    private static DiscordApi bot;
    private static Cache cache;
    private static DatabaseConnection connection;

    public static void initializeBot(){

        bot = new DiscordApiBuilder().setToken(SecretClass.getDiscordToken()).setAllIntents().login().join();
        Utils.LogSystem.log(prefix, "bot is ready on : " + bot.createBotInvite() + "515396586561", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        // TODO - Load cache from .json file
        cache = new Cache(null); // temp line

        connection = new DatabaseConnection();

        if(connection.getConnection() != null){

            bot.addMessageCreateListener(new nMessageCreateListener());
            initializeLogListeners();

            String status = "serving the server";
            bot.updateActivity(ActivityType.WATCHING, status);

            Utils.LogSystem.log(prefix, "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        }
        else
            bot.disconnect();

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

    public static Cache getCache() {
        return cache;
    }

    public static void setCache(Cache cache) {
        Bot.cache = cache;
    }

    public static DatabaseConnection getConnection() {
        return connection;
    }
}
