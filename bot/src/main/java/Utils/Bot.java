package Utils;

import Database.*;
import Instances.*;
import Listeners.nMessageCreateListener;
import Tasks.RotateStatusTask;
import Threads.ShutdownThread;
import com.google.gson.GsonBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.function.Consumer;

public class Bot {

    private static String prefix = "§ SoftBot §";
    private static String version = "1.0.0-alpha";
    private static DiscordApi bot;
    private static DatabaseConnection connection;
    private static CalendarInstance calendar;
    private static TeamUtilInstance teamUtil;
    private static RedditInstance reddit;
    private static ArrayList<AdminInstance> admins;
    private static Timer timer;

    public static void initializeBot(){

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        connection = new DatabaseConnection();
        Utils.LogSystem.log(prefix, "trying to connect to the database", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        if(connection != null){

            Utils.LogSystem.log(prefix, "successfully connected to the database", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            calendar = new CalendarInstance();
            teamUtil = new TeamUtilInstance();
            reddit = new RedditInstance();
            admins = new ArrayList<AdminInstance>();

            TeamCommandUtils.loadCalendarInstance(load_teams_success -> {

                if(load_teams_success){
                    DeveloperCommandUtils.loadAdmins(load_admins_success -> {

                        if(load_admins_success){
                            GameCommandUtils.loadCalendarInstance(load_calendar_success -> {

                                if(load_calendar_success){

                                    bot = new DiscordApiBuilder().setToken(SecretClass.getDiscordToken()).setAllIntents().login().join();
                                    Utils.LogSystem.log(prefix, "bot is ready on : " + bot.createBotInvite() + "515396586561", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                                    bot.addMessageCreateListener(new nMessageCreateListener());
                                    initializeLogListeners();

                                    teamUtil.recalculateMemberCount();

                                    timer = new Timer("softbot-timer");
                                    timer.schedule(new RotateStatusTask(), 0, 30000);

                                    Utils.LogSystem.log(prefix, "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                                    //saveCache(saved -> {});

                                }
                                else {
                                    Utils.LogSystem.log(prefix, "error while loading calendar. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                                }

                            });
                        }
                        else {
                            Utils.LogSystem.log(prefix, "error while loading admins. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                        }

                    });
                }
                else {
                    Utils.LogSystem.log(prefix, "error while loading teams. Turning app off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
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

    public static String getVersion() {
        return version;
    }

    public static ArrayList<AdminInstance> getAdmins() {
        return admins;
    }

    public static boolean removeAdminByDiscordId(long id){
        for(AdminInstance admin : admins){
            if(admin.getDiscord_id() == id){
                admins.remove(admin);
                return true;
            }
        }
        return false;
    }

    public static TeamUtilInstance getTeamUtil() {
        return teamUtil;
    }
}
