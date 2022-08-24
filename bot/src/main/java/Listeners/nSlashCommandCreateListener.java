package Listeners;

import Enums.ReplyEmbedEnum;
import SlashCommands.*;
import Utils.DiscordUtils;
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
                //OfferCommand.run(interaction); // TODO - activate this
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Omlouvám se, ale tato funkce ještě není plně hotová. Bot se nachází v pre-beta verzi a v plné verzi tato funkce spuštěna bude. Děkuji za pochopení", "nSlashCommandCreateListener.run", ReplyEmbedEnum.WARNING)).respond().join();
                break;
            case "inquiry":
                //InquiryCommand.run(interaction); // TODO - activate this
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Omlouvám se, ale tato funkce ještě není plně hotová. Bot se nachází v pre-beta verzi a v plné verzi tato funkce spuštěna bude. Děkuji za pochopení", "nSlashCommandCreateListener.run", ReplyEmbedEnum.WARNING)).respond().join();
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
            case "shop":
                ShopCommand.run(interaction);
                break;

        }

    }

}
