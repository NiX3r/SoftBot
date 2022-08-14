package Listeners;

import SlashCommands.BasicsCommand;
import Utils.Bot;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class nSlashCommandCreateListener implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {

        switch (event.getSlashCommandInteraction().getCommandName()){
            case "sb":
                BasicsCommand.run(event.getSlashCommandInteraction());
                break;
        }

    }

}
