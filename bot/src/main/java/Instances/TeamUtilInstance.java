package Instances;

import java.util.ArrayList;

public class TeamUtilInstance {

    private ArrayList<TeamInstance> teams;

    public TeamUtilInstance(){

        this.teams = new ArrayList<TeamInstance>();
    }

    public ArrayList<TeamInstance> getTeams() {
        return teams;
    }

    public int calculateTeamPages(){

        return (teams.size() / 10) + 1;

    }

    public void recalculateMemberCount(){

        for(TeamInstance team : teams){
            team.recalculateMemberCount();
        }

    }

    public ArrayList<TeamInstance> getTeamArrayByPageIndex(int index){

        ArrayList<TeamInstance> output = new ArrayList<TeamInstance>();

        for(int i = 10 * (index - 1); i < (9 + (10 * (index - 1))); i++){

            if(i == teams.size())
                break;

            output.add(teams.get(i));

        }

        System.out.println(output.size());

        return output;

    }

    public TeamInstance getTeamById(int id){
        for(TeamInstance team : teams){
            if(team.getId() == id)
                return team;
        }
        return null;
    }

}
