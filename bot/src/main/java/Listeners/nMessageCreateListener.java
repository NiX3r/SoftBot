package Listeners;

import Commands.GameCommand;
import Commands.OtherCommand;
import Commands.ProgrammerCommand;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

/*
    Class nMessageCreateListener

    Listener class for listening message commands
    split sub commands and run their interfaces

*/

public class nMessageCreateListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if(event.getMessage().getContent().split(" ").length < 2)
            return;

        if(event.getMessage().getContent().split(" ")[0].equals("!sb")){

            switch (event.getMessage().getContent().split(" ")[1]){

                case "game":
                    GameCommand.run(event);
                    break;

                default:
                    OtherCommand.run(event);

            }

        }
        else if(event.getMessage().getContent().split(" ")[0].equals("!sbp")){

            ProgrammerCommand.run(event);

        }

    }

}
