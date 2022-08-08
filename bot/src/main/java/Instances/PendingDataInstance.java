package Instances;

import java.util.ArrayList;

public class PendingDataInstance {

    private ArrayList<BazaarInstance> bazaar;
    private ArrayList<GameInstance> games;
    private ArrayList<TeamInstance> teams;

    public PendingDataInstance(){

        this.bazaar = new ArrayList<BazaarInstance>();
        this.games = new ArrayList<GameInstance>();
        this.teams = new ArrayList<TeamInstance>();

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
}
