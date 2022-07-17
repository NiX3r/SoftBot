package Utils;

import Instances.GameInstance;

import java.util.ArrayList;

public class Cache {

    private ArrayList<GameInstance> gameInstances;

    public Cache(ArrayList<GameInstance> gameInstances){

        if(gameInstances == null)
            gameInstances = new ArrayList<GameInstance>();
        else
            this.gameInstances = gameInstances;

    }

    public int isUserAlreadyInGameInstance(long user_id){
        for(GameInstance game : this.gameInstances){
            if(game.getCreate_user_id() == user_id){
                game.getId();
            }
        }
        return -1;
    }

    public ArrayList<GameInstance> getGameInstances() {
        return gameInstances;
    }

    public void setGameInstances(ArrayList<GameInstance> gameInstances) {
        this.gameInstances = gameInstances;
    }
}
