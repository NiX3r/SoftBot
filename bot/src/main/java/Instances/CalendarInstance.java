package Instances;

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

        System.out.println(output.size());

        return output;

    }

}
