package Instances;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class PendingDataInstance {

    private ArrayList<BazaarInstance> bazaar;
    private ArrayList<GameInstance> games;
    private ArrayList<TeamInstance> teams;
    private ArrayList<ShopInstance> shops;
    private HashMap<Long, Object> checkingData;

    public PendingDataInstance(){

        this.bazaar = new ArrayList<BazaarInstance>();
        this.games = new ArrayList<GameInstance>();
        this.teams = new ArrayList<TeamInstance>();
        this.shops = new ArrayList<ShopInstance>();
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

    public ArrayList<ShopInstance> getShops() {
        return shops;
    }
}
