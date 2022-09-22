package Utils;

import Database.*;
import Enums.LogTypeEnum;
import Instances.*;
import Listeners.nMessageComponentCreateListener;
import Listeners.nSlashCommandCreateListener;
import SlashCommands.SlashCommandUtils;
import Tasks.RotateStatusTask;
import Threads.ShutdownThread;
import com.google.gson.GsonBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.function.Consumer;

public class Bot {

    private static boolean isTest = false;
    private static String prefix = "§ SoftBot §";
    private static String version = "1.2.0-beta";
    private static DiscordApi bot;
    private static DatabaseConnection connection;
    private static CalendarInstance calendar;
    private static TeamUtilInstance teamUtil;
    private static RedditInstance reddit;
    private static PunishmentInstance punishments;
    private static BazaarUtilInstance bazaar;
    private static ShopUtilInstance shop;
    private static ArrayList<AdminInstance> admins;
    private static ArrayList<ServerOptionInstance> serverOptions;
    private static HashMap<Long, Object> checkingData; // Data being checked by admins
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
            shop = new ShopUtilInstance();
            admins = new ArrayList<AdminInstance>();
            serverOptions = new ArrayList<ServerOptionInstance>();
            checkingData = new HashMap<Long, Object>();

            DatabaseUtils.loadDataIntoCache(success -> {

                if(success){
                    bot = new DiscordApiBuilder().setToken(isTest ? SecretClass.getDiscordTestToken() : SecretClass.getDiscordToken()).setAllIntents().login().join();
                    Utils.LogSystem.log(LogTypeEnum.INFO, "bot is ready on : " + bot.createBotInvite() + "515396586561", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                    // Bot slash commands already created
                    //SlashCommandUtils.delete();
                    //SlashCommandUtils.create();

                    bot.addSlashCommandCreateListener(new nSlashCommandCreateListener());
                    bot.addMessageComponentCreateListener(new nMessageComponentCreateListener());
                    initializeLogListeners();

                    teamUtil.recalculateMemberCount();

                    checkWeekPlan();

                    timer = new Timer("softbot-timer");
                    timer.schedule(new RotateStatusTask(), 0, 30000);

                    Utils.LogSystem.log(LogTypeEnum.INFO, "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                    //saveCache(saved -> {});
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

    private static void checkWeekPlan(){

        if(LocalDate.now().getDayOfWeek().getValue() == 7){

            LocalDate now = LocalDate.now();
            long now_ms = DatabaseUtils.decodeDateTime(now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + " 00:00:00");
            final long day = 86400000;
            String[] games = new String[7];

            for(int i = 1; i < 8; i++){
                String today_games = GameUtils.getListGamesOnSpecificDate(now_ms + (i * day));
                if(today_games == "")
                    games[i - 1] = "žádné";
                else
                    games[i - 1] = today_games;
            }

            DiscordUtils.sendWeekPlanEmbed(games, now.getYear(), now.getMonthValue(), now.getDayOfMonth(), success -> {});

        }

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

    public static ShopUtilInstance getShop() {
        return shop;
    }

    public static HashMap<Long, Object> getCheckingData() {
        return checkingData;
    }
}
