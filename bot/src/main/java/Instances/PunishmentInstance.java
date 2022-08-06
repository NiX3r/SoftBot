package Instances;

import java.util.ArrayList;

public class PunishmentInstance {

    private ArrayList<BanInstance> bans;

    public PunishmentInstance() {
        this.bans = new ArrayList<BanInstance>();
    }

    public ArrayList<BanInstance> getBans() {
        return bans;
    }

    public BanInstance getBanByUserId(long id){
        for(BanInstance ban : bans){
            if(ban.getDiscord_user_id() == id)
                return ban;
        }
        return null;
    }

}
