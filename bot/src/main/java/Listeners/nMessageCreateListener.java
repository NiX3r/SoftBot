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

        if(event.getMessage().getContent().split(" ").length < 2)
            return;

        if(event.getMessage().getContent().split(" ")[0].equals("!sb")){

            if(Bot.getVersion().contains("alpha")){

                if(!event.getServer().isPresent()){
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota v soukromych zpravach, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                    return;
                }

                if(event.getServer().get().getIdAsString().equals("938754626025574420")){
                    classicCommand(event);
                }
                else {
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                }
                return;
            }

            classicCommand(event);

        }
        else if(event.getMessage().getContent().split(" ")[0].equals("!sba")){
            if(Bot.getVersion().contains("alpha")){

                if(!event.getServer().isPresent()){
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota v soukromych zpravach, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                    return;
                }

                if(event.getServer().get().getIdAsString().equals("938754626025574420")){
                    AdminCommand.run(event);
                }
                else {
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                }
                return;
            }
            AdminCommand.run(event);
        }
        else if(event.getMessage().getContent().split(" ")[0].equals("!sbp")){

            if(Bot.getVersion().contains("alpha")){

                if(!event.getServer().isPresent()){
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota v soukromych zpravach, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                    return;
                }

                if(event.getServer().get().getIdAsString().equals("938754626025574420")){
                    ProgrammerCommand.run(event);
                }
                else {
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Verze SoftBota", "Bohužel není možné v tuto chvíli využívat služeb SoftBota, jelikož je momentálně aktivní verze pro vývojáře a testerský tým. " +
                            "\n" +
                            "\nProsíme o strpení," +
                            "\n_SoftBot tým_", ReplyEmbedEnum.WARNING));
                }
                return;
            }
            ProgrammerCommand.run(event);

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

        }
    }

}
