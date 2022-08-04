package Utils;

import Database.DatabaseConnection;
import Database.DatabaseUtils;
import Instances.CalendarInstance;
import Instances.GameInstance;
import Instances.RedditInstance;
import Listeners.nMessageCreateListener;
import Threads.ShutdownThread;
import com.google.gson.GsonBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.function.Consumer;

public class Bot {

    private static String prefix = "§ SoftBot §";
    private static DiscordApi bot;
    private static DatabaseConnection connection;
    private static CalendarInstance calendar;
    private static RedditInstance reddit;

    public static void initializeBot(){

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        connection = new DatabaseConnection();
        Utils.LogSystem.log(prefix, "trying to connect to the database", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        if(connection != null){

            Utils.LogSystem.log(prefix, "successfully connected to the database", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            calendar = new CalendarInstance();
            reddit = new RedditInstance();

            DatabaseUtils.loadCalendarInstance(success -> {

                if(success){

                    bot = new DiscordApiBuilder().setToken(SecretClass.getDiscordToken()).setAllIntents().login().join();
                    Utils.LogSystem.log(prefix, "bot is ready on : " + bot.createBotInvite() + "515396586561", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                    bot.addMessageCreateListener(new nMessageCreateListener());
                    initializeLogListeners();

                    String status = "serving the server";
                    bot.updateActivity(ActivityType.WATCHING, status);

                    Utils.LogSystem.log(prefix, "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                    saveCache(saved -> {});

                }

            });

        }
        else {

            Utils.LogSystem.log(prefix, "can't connect to the database. Turning bot down ..", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        }

    }

    public static void saveCache(Consumer<Exception> consumer){

        String path = "./data/";
        new File(path).mkdirs();

        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    path + "/test-cache.json"));
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(calendar);
            f_writer.write(json);
            f_writer.flush();
            f_writer.close();
            LogSystem.log(Bot.getPrefix(), "cache saved", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(null);
        } catch (Exception e){
            LogSystem.log(Bot.getPrefix(), "can't save cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(e);
        }

    }

    public static DiscordApi getBot(){ return bot; }
    public static String getPrefix(){ return prefix; }
    public static Connection getConnection(){ return connection.getConnection(); }
    public static DatabaseConnection getDatabaseConnection() { return connection; }
    public static CalendarInstance getCalendar() { return calendar; }

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

    public static RedditInstance getReddit() {
        return reddit;
    }
}
