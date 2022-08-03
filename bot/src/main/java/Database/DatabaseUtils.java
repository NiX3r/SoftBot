package Database;

import Enums.GameStatusEnum;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Utils.Bot;
import Utils.UTFCorrectionTranslator;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Consumer;

public class DatabaseUtils {

    public static void loadCalendarInstance(Consumer<Boolean> callback){

        if(!Bot.getDatabaseConnection().isClosed()){

            Utils.LogSystem.log("PROGRAM", "loading calendar into cache", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            PreparedStatement statement = null;
            try {
                statement = Bot.getConnection()
                        .prepareStatement("SELECT * FROM Game WHERE Status='APPROVED'");

                ResultSet results = statement.executeQuery();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                while(results.next()){

                    Date start_date = sdf.parse(results.getObject("StartDate", String.class));
                    Date end_date = sdf.parse(results.getObject("EndDate", String.class));
                    Date edit_date = null;
                    if(results.getObject("LastEditDate", String.class) != null){
                        edit_date = sdf.parse(results.getObject("LastEditDate", String.class));
                    }
                    Date create_date = sdf.parse(results.getObject("CreateDate", String.class));
                    long start_ms = start_date.getTime();
                    long end_ms = end_date.getTime();
                    long edit_ms = edit_date == null ? 0 : edit_date.getTime();
                    long create_ms = create_date.getTime();
                    GameInstance mainInstance = new GameInstance(results.getInt("ID"),
                            UTFCorrectionTranslator.translate(results.getString("Name")),
                            results.getString("Thumbnail"),
                            start_ms,
                            end_ms,
                            results.getString("RepeatDate"),
                            results.getString("Website"),
                            UTFCorrectionTranslator.translate(results.getString("Location")),
                            results.getFloat("Price"),
                            UTFCorrectionTranslator.translate(results.getString("Type")),
                            UTFCorrectionTranslator.translate(results.getString("Description")),
                            GameStatusEnum.valueOf(results.getString("Status")),
                            Long.parseLong(String.valueOf(results.getDouble("LastEditAuthor") == 0.0 ? "0" : results.getDouble("LastEditAuthor"))),
                            edit_ms,
                            GameStatusEnum.valueOf(results.getString("LastEditStatus") == null ? "NULL" : results.getString("LastEditStatus")),
                            create_ms);

                    Bot.getCalendar().getGames().add(mainInstance);

                    long appendTime = 0;

                    switch (mainInstance.getRepeat_date()){

                        case "W":
                            appendTime = Long.parseLong("604800000");
                            break;

                        case "M":
                            appendTime = Long.parseLong("2629746000");
                            break;

                        case "Y":
                            appendTime = Long.parseLong("31556952000");
                            break;

                    }

                    if(appendTime == 0){

                        Bot.getCalendar().getCalendar().add(new CalendarGameInstance(mainInstance.getId(), mainInstance.getStart_date(), mainInstance.getEnd_date()));

                    }
                    else {

                        long game_length = mainInstance.getEnd_date() - mainInstance.getStart_date();

                        for(long index = mainInstance.getStart_date(); index >= System.currentTimeMillis() - Long.parseLong("31556952000"); index = index - appendTime){ // DECREASING

                            Bot.getCalendar().getCalendar().add(new CalendarGameInstance(mainInstance.getId(), index, index + game_length));

                        }

                        for(long index = mainInstance.getEnd_date(); index <= System.currentTimeMillis() + (2 * Long.parseLong("31556952000")); index = index + appendTime){

                            if(index == mainInstance.getEnd_date())
                                continue;

                            Bot.getCalendar().getCalendar().add(new CalendarGameInstance(mainInstance.getId(), index - game_length, index));

                        }

                    }

                }
            }catch (ParseException e) {
                Utils.LogSystem.log("PROGRAM", "error while parsing. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }
            catch (SQLException e) {
                Utils.LogSystem.log("PROGRAM", "error while sql communication. Message: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                callback.accept(false);
            }


        }

        Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));
        Utils.LogSystem.log("PROGRAM", "calendar successfully initialized, loaded and sorted", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        callback.accept(true);

    }

}
