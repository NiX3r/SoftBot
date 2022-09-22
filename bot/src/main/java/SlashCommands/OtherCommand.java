package SlashCommands;

import Database.ServerOptionUtils;
import Enums.ReplyEmbedEnum;
import Instances.RedditPostInstance;
import Instances.ServerOptionInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OtherCommand {

    public static void help(SlashCommandInteraction interaction){

        System.out.println("hello world");

        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Pomocné menu",
                "Pomocné menu naleznete na Wiki stránce\n https://softbot.ncodes.eu/wiki",
                "OtherCommands.help",
                ReplyEmbedEnum.SUCCESS))
                .respond().join();

    }

    public static void reddit(SlashCommandInteraction interaction){
        RedditPostInstance post = Bot.getReddit().getRandomPost();

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle(post.getTopic())
                .addField("Autor", post.getAuthor())
                .addField("Odkaz", "https://reddit.com" + post.getLink())
                .setImage(post.getContent())
                .setFooter("Zdroj: OtherCommand.run | Verze: " + Bot.getVersion());

        interaction.createImmediateResponder().addEmbed(builder).respond().join();
    }

    public static void invite(SlashCommandInteraction interaction){
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Pozvánka bota", "Odkaz na pozvání SoftBota na vlastní server\n\n**Link**\n http://softbot.ncodes.eu/discord", "OtherCommand.invite", ReplyEmbedEnum.SUCCESS)).respond().join();
    }

    public static void credits(SlashCommandInteraction interaction){
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Otroci projektu :muscle:",
                "**Vývojářský tým :technologist: **\n" +
                        "NiX3r (NiX3r#0272) - _hlavní vývojář, back-end Discord bot_\n" +
                        "Orim0 (Orim0#7985) - _web vývojář, back-end web services_\n" +
                        "KiJudo (KiJudo#3946) - _designérka, design web UI_\n" +
                        "\n" +
                        "**PR tým :office_worker: **\n" +
                        "Ravenbie (Ravenbie#8833) - _PR, zajištění slevových kódů_\n" +
                        "\n" +
                        "**Testeři :bug: **\n" +
                        "Kykrovec (Kykrobie#4976) - _tester, testování v alpha verzi_",
                "OtherCommand.credits",
                ReplyEmbedEnum.EASTER_EGG)).respond().join();
    }

    public static void sponsors(SlashCommandInteraction interaction){
        List<String> sponsors = new ArrayList<>();
        String sponsorss = "";
        Bot.getBot().getServerById("611985124023730185").ifPresent(server -> {
            server.getRoleById("1005966989644271746").ifPresent(role -> {
                role.getUsers().forEach(u -> sponsors.add(u.getName()));
            });
        });
        for(String sponsor : sponsors){
            sponsorss += sponsor + "\n";
        }
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Sponzoři projektu",
                "**Hlavní sponzoři projektu, díky kterým se mohl SoftBot spustit :heart: **\n" +
                        "Ravenbie (Ravenbie#8833) - 250 Kč\n" +
                        "KiJudo (KiJudo#3946) - 50 Kč\n" +
                        "\n" +
                        "**Sponzoři přes BuyMeACoffee**\n" +
                        sponsorss
                ,"OtherCommand.sponsors",
                ReplyEmbedEnum.EASTER_EGG)).respond().join();
    }

    public static void channel(SlashCommandInteraction interaction){
        long server_id = interaction.getServer().get().getId();

        interaction.getArguments().get(0).getChannelValue().ifPresent(channel -> {

            if(channel.asTextChannel().isPresent()){
                
                long channel_id = channel.getId();

                switch (interaction.getOptions().get(0).getName()){
                    case "system":
                        announcementChannel(server_id, channel_id, interaction);
                        break;
                    case "game":
                        gameChannel(server_id, channel_id, interaction);
                        break;
                    case "bazaar":
                        bazaarChannel(server_id, channel_id, interaction);
                        break;
                    case "team":
                        teamChannel(server_id, channel_id, interaction);
                        break;
                }
                
            }

        });
    }

    public static void teamRole(SlashCommandInteraction interaction){
        
        long server_id = interaction.getServer().get().getId();

        interaction.getArguments().get(0).getRoleValue().ifPresent(role -> {
            if(Bot.isServerOptionInList(server_id)){

                Bot.getServerOption(server_id).setTeam_member_role_id(role.getId());
                ServerOptionUtils.editMemberRole(server_id, role.getId(), edit_success -> {

                    if(edit_success){
                        Bot.getTeamUtil().getTeamByDiscordId(server_id).recalculateMemberCount();
                        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit týmovou roli.", "OtherCommand.teamRole", ReplyEmbedEnum.SUCCESS)).respond().join();
                    }
                    else{
                        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", "OtherCommand.teamRole", ReplyEmbedEnum.APP_ERROR)).respond().join();
                    }

                });

            }
            else {

                ServerOptionInstance serverOption = new ServerOptionInstance(server_id, -1, -1, -1, -1, role.getId());
                Bot.getServerOptions().add(serverOption);
                ServerOptionUtils.addServerOption(server_id, -1, -1, -1, -1, role.getId(), add_success -> {
                    Bot.getTeamUtil().getTeamByDiscordId(server_id).recalculateMemberCount();
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit týmovou roli.", "OtherCommand.teamRole", ReplyEmbedEnum.SUCCESS)).respond().join();

                });

            }
        });
    }

    private static void announcementChannel(long server_id, long channel_id, SlashCommandInteraction interaction){
        if(Bot.isServerOptionInList(server_id)){

            Bot.getServerOption(server_id).setAnnouncement_channel_id(channel_id);
            ServerOptionUtils.editChannel(server_id, channel_id, "", edit_success -> {

                if(edit_success){
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál.", "OtherCommand.announcementChannel", ReplyEmbedEnum.SUCCESS)).respond().join();
                }
                else{
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", "OtherCommand.announcementChannel", ReplyEmbedEnum.ERROR)).respond().join();
                }

            });

        }
        else {

            ServerOptionInstance serverOption = new ServerOptionInstance(server_id, channel_id, -1, -1, -1, -1);
            Bot.getServerOptions().add(serverOption);
            ServerOptionUtils.addServerOption(server_id, channel_id, -1, -1, -1, -1, add_success -> {

                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál.", "OtherCommand.announcementChannel", ReplyEmbedEnum.SUCCESS)).respond().join();

            });

        }
    }

    private static void gameChannel(long server_id, long channel_id, SlashCommandInteraction interaction){
        if(Bot.isServerOptionInList(server_id)){

            Bot.getServerOption(server_id).setGame_announcement_channel_id(channel_id);
            ServerOptionUtils.editChannel(server_id, channel_id, "Game", edit_success -> {

                if(edit_success){
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál pro hry.", "OtherCommand.gameChannel", ReplyEmbedEnum.SUCCESS)).respond().join();
                }
                else{
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", "OtherCommand.gameChannel", ReplyEmbedEnum.ERROR)).respond().join();
                }

            });

        }
        else {

            ServerOptionInstance serverOption = new ServerOptionInstance(server_id, -1, channel_id, -1, -1, -1);
            Bot.getServerOptions().add(serverOption);
            ServerOptionUtils.addServerOption(server_id, -1, channel_id, -1, -1, -1, add_success -> {

                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál pro hry.", "OtherCommand.gameChannel", ReplyEmbedEnum.SUCCESS)).respond().join();

            });

        }
    }

    private static void bazaarChannel(long server_id, long channel_id, SlashCommandInteraction interaction){
        if(Bot.isServerOptionInList(server_id)){

            Bot.getServerOption(server_id).setBazaar_announcement_channel_id(channel_id);
            ServerOptionUtils.editChannel(server_id, channel_id, "Bazaar", edit_success -> {

                if(edit_success){
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál pro bazar.", "OtherCommand.bazaarChannel", ReplyEmbedEnum.SUCCESS)).respond().join();
                }
                else{
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", "OtherCommand.bazaarChannel", ReplyEmbedEnum.ERROR)).respond().join();
                }

            });

        }
        else {

            ServerOptionInstance serverOption = new ServerOptionInstance(server_id, -1, -1,  channel_id, -1, -1);
            Bot.getServerOptions().add(serverOption);
            ServerOptionUtils.addServerOption(server_id, -1, -1, channel_id, -1, -1, add_success -> {

                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál pro bazar.", "OtherCommand.bazaarChannel", ReplyEmbedEnum.SUCCESS)).respond().join();

            });

        }
    }

    private static void teamChannel(long server_id, long channel_id, SlashCommandInteraction interaction){
        if(Bot.isServerOptionInList(server_id)){

            Bot.getServerOption(server_id).setTeam_announcement_channel_id(channel_id);
            ServerOptionUtils.editChannel(server_id, channel_id, "Team", edit_success -> {

                if(edit_success){
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál pro tým.", "OtherCommand.teamChannel", ReplyEmbedEnum.SUCCESS)).respond().join();
                }
                else{
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", "OtherCommand.teamChannel", ReplyEmbedEnum.ERROR)).respond().join();
                }

            });

        }
        else {

            ServerOptionInstance serverOption = new ServerOptionInstance(server_id, -1, -1, -1, channel_id, -1);
            Bot.getServerOptions().add(serverOption);
            ServerOptionUtils.addServerOption(server_id, -1, -1, -1, channel_id, -1, add_success -> {

                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál pro tým.", "OtherCommand.teamChannel", ReplyEmbedEnum.SUCCESS)).respond().join();

            });

        }
    }

}
