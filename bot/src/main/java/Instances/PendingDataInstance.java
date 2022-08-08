package Instances;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class PendingDataInstance {

    private ArrayList<BazaarInstance> bazaar;
    private ArrayList<GameInstance> games;
    private ArrayList<TeamInstance> teams;
    private HashMap<Long, Object> checkingData;

    public PendingDataInstance(){

        this.bazaar = new ArrayList<BazaarInstance>();
        this.games = new ArrayList<GameInstance>();
        this.teams = new ArrayList<TeamInstance>();
        checkingData = new HashMap<Long, Object>();

    }

    public ArrayList<BazaarInstance> getBazaar() {
        return bazaar;
    }

    public ArrayList<GameInstance> getGames() {
        return games;
    }

    public ArrayList<TeamInstance> getTeams() {
        return teams;
    }

    public HashMap<Long, Object> getCheckingData() {
        return checkingData;
    }
}
