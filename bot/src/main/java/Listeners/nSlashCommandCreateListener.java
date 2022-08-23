package Listeners;

import SlashCommands.*;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class nSlashCommandCreateListener implements SlashCommandCreateListener {

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {

        SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        switch (interaction.getCommandName()){
            case "help":
                OtherCommand.help(interaction);
                break;
            case "game":
                GameCommand.run(interaction);
                break;
            case "team":
                TeamCommand.run(interaction);
                break;
            case "offer":
                OfferCommand.run(interaction);
                break;
            case "inquiry":
                InquiryCommand.run(interaction);
                break;
            case "reddit":
                OtherCommand.reddit(interaction);
                break;
            case "invite":
                OtherCommand.invite(interaction);
                break;
            case "credits":
                OtherCommand.credits(interaction);
                break;
            case "sponsors":
                OtherCommand.sponsors(interaction);
                break;
            case "channel":
                OtherCommand.channel(interaction);
                break;
            case "team-role":
                OtherCommand.teamRole(interaction);
                break;
            case "ban":
                BotAdminCommand.ban(interaction);
                break;
            case "unban":
                BotAdminCommand.unban(interaction);
                break;
            case "server":
                BotAdminCommand.server(interaction);
                break;
            case "announcement":
                BotAdminCommand.announcement(interaction);
                break;
            case "admin":
                AdminCommand.run(interaction);
                break;
            case "pending":
                BotAdminCommand.pending(interaction);
                break;

        }

    }

}
