package Utils;

import SlashCommands.CreateDefaultSlashCommand;
import Database.*;
import Enums.LogTypeEnum;
import Instances.*;
import Listeners.nMessageComponentCreateListener;
import Listeners.nMessageCreateListener;
import Listeners.nSlashCommandCreateListener;
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

    private static boolean isTest = false;
    private static String prefix = "§ SoftBot §";
    private static String version = "1.1.0-alpha";
    private static DiscordApi bot;
    private static DatabaseConnection connection;
    private static CalendarInstance calendar;
    private static TeamUtilInstance teamUtil;
    private static RedditInstance reddit;
    private static PunishmentInstance punishments;
    private static BazaarUtilInstance bazaar;
    private static ArrayList<AdminInstance> admins;
    private static PendingDataInstance pendingData;
    private static ArrayList<ServerOptionInstance> serverOptions;
    private static Timer timer;

    public static void initializeBot(String argument){

        if(argument.equals("test"))
            isTest = true;

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        connection = new DatabaseConnection();
        Utils.LogSystem.log(LogTypeEnum.INFO, "trying to connect to the database", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        if(connection != null){

            Utils.LogSystem.log(LogTypeEnum.INFO, "successfully connected to the database", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            calendar = new CalendarInstance();
            teamUtil = new TeamUtilInstance();
            reddit = new RedditInstance();
            punishments = new PunishmentInstance();
            bazaar = new BazaarUtilInstance();
            pendingData = new PendingDataInstance();
            admins = new ArrayList<AdminInstance>();
            serverOptions = new ArrayList<ServerOptionInstance>();

            TeamUtils.loadPendingTeamsInstance(load_pending_team_success -> {
                if(load_pending_team_success){
                    GameUtils.loadPendingCalendarInstance(load_pending_game_success -> {
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
                                                                            GameUtils.loadCalendarInstance(load_calendar_success -> {

                                                                                if(load_calendar_success){

                                                                                    bot = new DiscordApiBuilder().setToken(isTest ? SecretClass.getDiscordTestToken() : SecretClass.getDiscordToken()).setAllIntents().login().join();
                                                                                    Utils.LogSystem.log(LogTypeEnum.INFO, "bot is ready on : " + bot.createBotInvite() + "515396586561", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                                                                                    CreateDefaultSlashCommand.create();
                                                                                    bot.addSlashCommandCreateListener(new nSlashCommandCreateListener());
                                                                                    bot.addMessageCreateListener(new nMessageCreateListener());
                                                                                    bot.addMessageComponentCreateListener(new nMessageComponentCreateListener());
                                                                                    initializeLogListeners();

                                                                                    teamUtil.recalculateMemberCount();

                                                                                    timer = new Timer("softbot-timer");
                                                                                    timer.schedule(new RotateStatusTask(), 0, 30000);

                                                                                    Utils.LogSystem.log(LogTypeEnum.INFO, "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                                                                                    //saveCache(saved -> {});

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
        else {

            Utils.LogSystem.log(LogTypeEnum.ERROR, "can't connect to the database. Turning bot down ..", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

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
            LogSystem.log(LogTypeEnum.INFO, "cache saved", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(null);
        } catch (Exception e){
            LogSystem.log(LogTypeEnum.ERROR, "can't save cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
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
            Utils.LogSystem.log(LogTypeEnum.ERROR, "lost connection", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
        bot.addReconnectListener(listener -> {
            Utils.LogSystem.log(LogTypeEnum.WARNING, "reconnecting", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
        bot.addResumeListener(listener -> {
            Utils.LogSystem.log(LogTypeEnum.WARNING, "resuming", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
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

    public static boolean isUserAdmin(long id){
        for(AdminInstance admin : admins){
            if(admin.getDiscord_id() == id)
                return true;
        }
        return false;
    }

    public static TeamUtilInstance getTeamUtil() {
        return teamUtil;
    }

    public static PunishmentInstance getPunishments() {
        return punishments;
    }

    public static ArrayList<ServerOptionInstance> getServerOptions() {
        return serverOptions;
    }

    public static ServerOptionInstance getServerOption(long server_id){
        for(ServerOptionInstance serverOption : serverOptions){
            if(serverOption.getServer_id() == server_id)
                return serverOption;
        }
        return null;
    }

    public static boolean isServerOptionInList(long server_id){
        for(ServerOptionInstance serverOption : serverOptions){
            if(serverOption.getServer_id() == server_id)
                return true;
        }
        return false;
    }

    public static boolean isIsTest() {
        return isTest;
    }

    public static BazaarUtilInstance getBazaar() {
        return bazaar;
    }

    public static PendingDataInstance getPendingData() {
        return pendingData;
    }
}
