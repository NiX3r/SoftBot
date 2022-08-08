package Commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;

public class PendingCommand {

    // Command template
    // !sba pending [game | team | offer | inquiry | shop]
    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        switch (splitter[2]){

            case "game":

                break;

            case "team":

                break;

            case "offer":

                break;

            case "inquiry":

                break;

            case "shop":

                break;

        }

    }

    private static void game(Message msg){



    }

}
