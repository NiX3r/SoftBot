package Listeners;

import Commands.GameCommand;
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

        /*if(!event.getMessageAuthor().isYourself()){ // Way to ping Discord bot
            event.getMessageAuthor().asWebhook().ifPresent(webhook -> {

                event.getMessage().reply("HELLO WORLD");

            });
        }*/

        if(event.getMessage().getContent().split(" ")[0].equals("!sb")){

            switch (event.getMessage().getContent().split(" ")[1]){

                case "game":
                    GameCommand.run(event);
                    break;

            }

        }

    }

}
