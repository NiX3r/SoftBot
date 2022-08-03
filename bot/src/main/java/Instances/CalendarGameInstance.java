package Instances;

public class CalendarGameInstance {

    private int root_game_id;
    private long start_date;
    private long end_date;

    public CalendarGameInstance(int root_game_id, long start_date, long end_date){

        this.root_game_id = root_game_id;
        this.start_date = start_date;
        this.end_date = end_date;

    }

    public int getRoot_game_id() {
        return root_game_id;
    }

    public long getStart_date() {
        return start_date;
    }

    public long getEnd_date() {
        return end_date;
    }
}
