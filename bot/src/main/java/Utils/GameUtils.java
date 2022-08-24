package Utils;

import Instances.GameInstance;

public class GameUtils {

    public static String getListGamesOnSpecificDate(long at_date){

        String output = "";
        final long day_length_ms = 86400000;

        for(int index = 0; index < Bot.getCalendar().getCalendar().size(); index++){

            if(Bot.getCalendar().getCalendar().get(index).getStart_date() < at_date)
                continue;

            if(Bot.getCalendar().getCalendar().get(index).getStart_date() > at_date + day_length_ms)
                break;

            int root_id = Bot.getCalendar().getCalendar().get(index).getRoot_game_id();

            output += "<<" + root_id + ">> (" + root_id + "), ";

        }

        for(GameInstance game : Bot.getCalendar().getGames()){
            output = output.replaceAll("<<" + game.getId() + ">>", game.getName());
        }

        return output;

    }

}
