package Tasks;

import Utils.Bot;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.server.Server;

import java.util.TimerTask;

public class RotateStatusTask extends TimerTask {

    int index = 0;

    @Override
    public void run() {

        switch (index){

            case 0:

                int total_members = 0;
                for (Server server : Bot.getBot().getServers()){
                    total_members += server.getMemberCount();
                }
                Bot.getBot().updateActivity(ActivityType.WATCHING, "celkem " + total_members + " uživatelů");
                index++;

                break;

            case 1:

                int total_games = Bot.getCalendar().getGames().size();
                Bot.getBot().updateActivity(ActivityType.WATCHING, "celkem " + total_games + " her");
                index++;

                break;

            case 2:

                int total_servers = Bot.getBot().getServers().size();
                Bot.getBot().updateActivity(ActivityType.WATCHING, "celkem " + total_servers + " serverů");
                index++;

                break;

            case 3:

                int total_teams = Bot.getTeamUtil().getTeams().size();
                Bot.getBot().updateActivity(ActivityType.WATCHING, "celkem " + total_teams + " týmů");
                index++;

                break;

            default:
                index = 0;

        }

    }

}
