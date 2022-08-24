package SlashCommands;

import Enums.ReplyEmbedEnum;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ServerCommand {

    public static void run(SlashCommandInteraction interaction){

        switch (interaction.getOptions().get(0).getName()){

            case "list":
                list(interaction);
                break;
            case "link":
                link(interaction);
                break;
            case "disconnect":
                disconnect(interaction);
                break;
            case "ban":
                ban(interaction);
                break;
            case "unban":
                unban(interaction);
                break;

        }

    }

    private static void list(SlashCommandInteraction interaction) {
        String msg = "";
        for (Server server : Bot.getBot().getServers()){
            msg += server.getName() + ", ID: " + server.getId() + ", Počet uživatelů: " + server.getMemberCount() + "\n";
        }
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("stránka x/x", msg, "ServerCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();
    }

    private static void link(SlashCommandInteraction interaction) {
        String server_id = interaction.getArguments().get(0).getStringValue().get();
        if(Bot.getBot().getServerById(server_id).isPresent()){
            Bot.getBot().getServerById(server_id).ifPresent(server -> {
                if(server.getSystemChannel().isPresent()){
                    server.getSystemChannel().ifPresent(channel -> {
                        channel.createInviteBuilder().create().thenAccept(finalInvite -> {
                            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), "ServerCommand.link", ReplyEmbedEnum.SUCCESS)).respond().join();
                        }).exceptionally(err -> {
                            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("", "Nastala chyba aplikace. Prosím kontaktuje vývojáře.\n\nChybová hláška\n" + err.getMessage(), "ServerCommand.link", ReplyEmbedEnum.APP_ERROR)).respond().join();
                            return null;
                        });
                    });
                }
                else {
                    server.getTextChannels().get(0).createInviteBuilder().create().thenAccept(finalInvite -> {
                        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), "ServerCommand.link", ReplyEmbedEnum.SUCCESS)).respond().join();
                    });
                    for(ServerTextChannel channel : server.getTextChannels()){
                        if(channel.canSee(server.getEveryoneRole().getUsers().iterator().next())){
                            channel.createInviteBuilder().create().thenAccept(finalInvite -> {
                                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), "ServerCommand.link", ReplyEmbedEnum.SUCCESS)).respond().join();
                            }).exceptionally(err -> {
                                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("", "Nastala chyba aplikace. Prosím kontaktuje vývojáře.\n\nChybová hláška\n" + err.getMessage(), "ServerCommand.link", ReplyEmbedEnum.APP_ERROR)).respond().join();
                                return null;
                            });
                        }
                    }
                }
            });
        }
        else {
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující ID", "Zadal jsi neexistující ID serveru. Nebo na tom serveru není připojený SoftBot.`", "ServerCommand.link", ReplyEmbedEnum.ERROR)).respond().join();
            return;
        }
    }

    private static void disconnect(SlashCommandInteraction interaction) {

        String server_id = interaction.getArguments().get(0).getStringValue().get();
        String reason = interaction.getArguments().get(1).getStringValue().get();
        if(Bot.getBot().getServerById(server_id).isPresent()){
            Bot.getBot().getServerById(server_id).ifPresent(server -> {
                server.leave().thenAccept(success_leave -> {
                    // TODO - Add action into database
                    server.getOwner().ifPresent(owner -> {
                        owner.sendMessage(DiscordUtils.createReplyEmbed("Odpojení bota od serveru", "Byl jsem odpojen ze serveru `" + server.getName() + "` administrátorem `" + interaction.getUser().getName() + "`.\nDůvod\n```" + reason + "```", "ServerCommand.disconnect", ReplyEmbedEnum.WARNING)).join();
                    });
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi odpojil SoftBota od serveru '" + server.getName() +"'", "ServerCommand.disconnect", ReplyEmbedEnum.SUCCESS)).respond().join();
                });
            });
        }
        else {
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující ID", "Zadal jsi neexistující ID serveru. Nebo na tom serveru není připojený SoftBot.`", "ServerCommand.disconnect", ReplyEmbedEnum.ERROR)).respond().join();
            return;
        }

    }

    public static void ban(SlashCommandInteraction interaction){
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "BotAdminCommand.run", ReplyEmbedEnum.WARNING));
    }

    public static void unban(SlashCommandInteraction interaction){
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "BotAdminCommand.run", ReplyEmbedEnum.WARNING));
    }

}
