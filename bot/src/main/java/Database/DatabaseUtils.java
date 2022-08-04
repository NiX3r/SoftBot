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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Consumer;

public class DatabaseUtils {

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

        return String.valueOf(id);

    }

}
