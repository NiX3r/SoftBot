package Listeners;

import Commands.*;
import Enums.ReplyEmbedEnum;
import Utils.Bot;
import Utils.DiscordUtils;
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

        String[] splitter = event.getMessage().getContent().split(" ");

        if(splitter[0].equals("!sb") || splitter[0].equals("!sba") || splitter[0].equals("!sbp") ||
            splitter[0].equals("t!sb") || splitter[0].equals("t!sba") || splitter[0].equals("t!sbp")){
            if(Bot.getVersion().contains("alpha")){
                if(!event.getServer().isPresent()){
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota v soukromych zpravach, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                    return;
                }
                if(!event.getServer().get().getIdAsString().equals("938754626025574420")){
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                    return;
                }
            }
            if(Bot.isIsTest()){
                switch (event.getMessage().getContent().split(" ")[0]){
                    case "t!sb":
                        classicCommand(event);
                        break;
                    case "t!sba":
                        AdminCommand.run(event);
                        break;
                    case "t!sbp":
                        ProgrammerCommand.run(event);
                        break;
                }
                return;
            }
            else {
                switch (event.getMessage().getContent().split(" ")[0]){
                    case "!sb":
                        classicCommand(event);
                        break;
                    case "!sba":
                        AdminCommand.run(event);
                        break;
                    case "!sbp":
                        ProgrammerCommand.run(event);
                        break;
                }
                return;
            }
        }

    }

    private void classicCommand(MessageCreateEvent event){
        switch (event.getMessage().getContent().split(" ")[1]){

            case "game":
                GameCommand.run(event);
                break;

            case "team":
                TeamCommand.run(event);
                break;

            default:
                OtherCommand.run(event);
                break;

        }
    }

}
