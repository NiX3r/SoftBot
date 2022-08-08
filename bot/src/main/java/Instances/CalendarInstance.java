package Instances;

import Utils.Bot;

import java.util.ArrayList;

public class CalendarInstance {

    private ArrayList<CalendarGameInstance> calendar;
    private ArrayList<GameInstance> games;

    public CalendarInstance(){

        this.calendar = new ArrayList<CalendarGameInstance>();
        this.games = new ArrayList<GameInstance>();

    }

    public ArrayList<CalendarGameInstance> getCalendar() {
        return this.calendar;
    }

    public void setCalendar(ArrayList<CalendarGameInstance> games) {
        this.calendar = games;
    }

    public ArrayList<GameInstance> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameInstance> games) {
        this.games = games;
    }

    public GameInstance getGameById(int id){
        for(GameInstance game : this.games){
            if(game.getId() == id)
                return game;
        }
        return null;
    }

    public int calculateGamePages(){

        return (games.size() / 10) + 1;

    }

    public ArrayList<GameInstance> getGameArrayByPageIndex(int index){

        ArrayList<GameInstance> output = new ArrayList<GameInstance>();

        for(int i = 10 * (index - 1); i < (9 + (10 * (index - 1))); i++){

            if(i == games.size())
                break;

            output.add(games.get(i));

        }

        return output;

    }

    public void addCalendarGame(GameInstance mainInstance){

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

            calendar.add(new CalendarGameInstance(mainInstance.getId(), mainInstance.getStart_date(), mainInstance.getEnd_date()));

        }
        else {

            long game_length = mainInstance.getEnd_date() - mainInstance.getStart_date();

            for(long index = mainInstance.getStart_date(); index >= System.currentTimeMillis() - Long.parseLong("31556952000"); index = index - appendTime){ // DECREASING

                calendar.add(new CalendarGameInstance(mainInstance.getId(), index, index + game_length));

            }

            for(long index = mainInstance.getEnd_date(); index <= System.currentTimeMillis() + (2 * Long.parseLong("31556952000")); index = index + appendTime){

                if(index == mainInstance.getEnd_date())
                    continue;

                calendar.add(new CalendarGameInstance(mainInstance.getId(), index - game_length, index));

            }

        }

    }

}
