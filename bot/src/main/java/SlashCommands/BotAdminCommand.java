package SlashCommands;

import Enums.ReplyEmbedEnum;
import Instances.ServerOptionInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

public class BotAdminCommand {

    public static void announcement(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            String content = interaction.getArguments().get(0).getStringValue().get().replaceAll("<n>", "\n");
            Bot.getBot().getServers().forEach(server -> {

                ServerOptionInstance option = Bot.getServerOption(server.getId());

                if(option == null){
                    for(ServerTextChannel channel : server.getTextChannels()){
                        if(channel.asPrivateChannel().isPresent())
                            continue;
                        channel.sendMessage(DiscordUtils.createAnnouncementEmbed(content + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `!sb channel <označení místnosti>`", interaction.getUser().getMentionTag().toString(), interaction.getUser().getAvatar().getUrl().toString())).join();
                        break;
                    }
                }
                else {
                    long announcement_channel_id = option.getAnnouncement_channel_id();

                    server.getTextChannelById(announcement_channel_id).ifPresent(channel -> {
                        channel.sendMessage(DiscordUtils.createAnnouncementEmbed(content, interaction.getUser().getMentionTag().toString(), interaction.getUser().getAvatar().getUrl().toString()));
                    });
                }

            });
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Zasláno", "Upozornění bylo úspěšně zasláno na všechny servery.", "BotAdminCommand.announcement", ReplyEmbedEnum.SUCCESS)).respond().join();
        }
    }

    public static void ban(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "BotAdminCommand.run", ReplyEmbedEnum.WARNING));
        }
    }

    public static void unban(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "BotAdminCommand.run", ReplyEmbedEnum.WARNING));
        }
    }

    public static void server(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            ServerCommand.run(interaction);
        }
    }

    private static boolean checkPermission(SlashCommandInteraction interaction){
        if(!Bot.isUserAdmin(interaction.getUser().getId())){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro Admin tým SoftBota", "AdminCommand.run", ReplyEmbedEnum.WARNING)).respond().join();
            return false;
        }
        return true;
    }

}
