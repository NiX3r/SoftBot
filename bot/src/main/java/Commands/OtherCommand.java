package Commands;

import Database.ServerOptionUtils;
import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.RedditPostInstance;
import Instances.ServerOptionInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OtherCommand {

    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        Utils.LogSystem.log(LogTypeEnum.INFO, "other comand catched by '" + event.getMessageAuthor().getName() + "' on server '" + (event.getServer().isPresent() ? event.getServer().get().getName() : "PrivateMessage") + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        switch (splitter[1]){

            case "reddit":
                reddit(splitter, event.getMessage());
                break;

            case "invite":
                invite(splitter, event.getMessage());
                break;

            case "credits":
                credits(splitter, event.getMessage());
                break;

            case "sponsors":
                sponsors(splitter, event.getMessage());
                break;

            case "help":
                help(splitter, event.getMessage());
                break;

            case "channel":
                channel(splitter, event.getMessage());
                break;

            case "team-role":
                teamRole(splitter, event.getMessage());
                break;

            case "offer":
                OfferCommand.run(event);
                break;

            case "inquiry":
                InquiryCommand.run(event);
                break;

            default:
                event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný příkaz.\n\nPro nápovědu\n`!sb help`", "OtherCommand.run", ReplyEmbedEnum.ERROR));
                break;

        }

    }

    private static void teamRole(String[] splitter, Message message) {
        if(message.getServer().isPresent()){
            if(message.getAuthor().isServerAdmin()){

                if(Bot.getTeamUtil().hasTeamSetupDiscord(message.getServer().get().getId())){
                    if(message.getMentionedRoles().size() == 1){
                        message.getServer().ifPresent(server -> {

                            if(Bot.isServerOptionInList(server.getId())){

                                Bot.getServerOption(server.getId()).setTeam_member_role_id(message.getMentionedRoles().get(0).getId());
                                ServerOptionUtils.editMemberRole(server.getId(), message.getMentionedRoles().get(0).getId(), edit_success -> {

                                    if(edit_success){
                                        Bot.getTeamUtil().getTeamByDiscordId(server.getId()).recalculateMemberCount();
                                        message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit týmovou roli.", "OtherCommand.teamRole", ReplyEmbedEnum.SUCCESS));
                                    }
                                    else{
                                        message.reply(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", "OtherCommand.teamRole", ReplyEmbedEnum.APP_ERROR));
                                    }

                                });

                            }
                            else {

                                ServerOptionInstance serverOption = new ServerOptionInstance(server.getId(), -1, message.getMentionedRoles().get(0).getId());
                                Bot.getServerOptions().add(serverOption);
                                ServerOptionUtils.addServerOption(server.getId(), -1, message.getMentionedRoles().get(0).getId(), add_success -> {
                                    Bot.getTeamUtil().getTeamByDiscordId(server.getId()).recalculateMemberCount();
                                    message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit týmovou roli.", "OtherCommand.teamRole", ReplyEmbedEnum.SUCCESS));

                                });

                            }

                        });
                    }
                    else {
                        message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Pro správné využítí tohoto příkazu musíš označit roli.\n\nFormát příkazu\n`!sb team-role <označení role>`", "OtherCommand.teamRole", ReplyEmbedEnum.WARNING));
                    }
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("Nenastavenost", "Tento server není nastaven jako týmový server. Prosím upravte jej v sekcí týmu", "OtherCommand.teamRole", ReplyEmbedEnum.WARNING));
                }
            }
            else {
                message.reply(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro administrátory tohoto serveru", "OtherCommand.teamRole", ReplyEmbedEnum.WARNING));
            }
        }
        else {
            message.reply(DiscordUtils.createReplyEmbed("Lokace příkazu", "Tento příkaz lze vykonat pouze na serveru", "OtherCommand.teamRole", ReplyEmbedEnum.WARNING));
        }
    }

    private static void channel(String[] splitter, Message message) {

        if(message.getServer().isPresent()){
            if(message.getAuthor().isServerAdmin()){
                if(message.getMentionedChannels().size() == 1){
                    message.getServer().ifPresent(server -> {

                        if(Bot.isServerOptionInList(server.getId())){

                            Bot.getServerOption(server.getId()).setAnnouncement_channel_id(message.getMentionedChannels().get(0).getId());
                            ServerOptionUtils.editChannel(server.getId(), message.getMentionedChannels().get(0).getId(), edit_success -> {

                                if(edit_success){
                                    message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál.", "OtherCommand.channel", ReplyEmbedEnum.SUCCESS));
                                }
                                else{
                                    message.reply(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", "OtherCommand.channel", ReplyEmbedEnum.APP_ERROR));
                                }

                            });

                        }
                        else {

                            ServerOptionInstance serverOption = new ServerOptionInstance(server.getId(), message.getMentionedChannels().get(0).getId(), -1);
                            Bot.getServerOptions().add(serverOption);
                            ServerOptionUtils.addServerOption(server.getId(), message.getMentionedChannels().get(0).getId(), -1, add_success -> {

                                message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál.", "OtherCommand.channel", ReplyEmbedEnum.SUCCESS));

                            });

                        }

                    });
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Pro správné využítí tohoto příkazu musíš označit kanál.\n\nFormát příkazu\n`!sb channel <označení kanálu>`", "OtherCommand.channel", ReplyEmbedEnum.WARNING));
                }

            }
            else {
                message.reply(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro administrátory tohoto serveru", "OtherCommand.channel", ReplyEmbedEnum.WARNING));
            }
        }
        else {
            message.reply(DiscordUtils.createReplyEmbed("Lokace příkazu", "Tento příkaz lze vykonat pouze na serveru", "OtherCommand.channel", ReplyEmbedEnum.WARNING));
        }

    }

    private static void reddit(String[] splitter, Message msg){

        RedditPostInstance post = Bot.getReddit().getRandomPost();

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle(post.getTopic())
                .addField("Autor", post.getAuthor())
                .addField("Odkaz", "https://reddit.com" + post.getLink())
                .setImage(post.getContent())
                .setFooter("Zdroj: OtherCommand.run | Verze: " + Bot.getVersion());

        msg.reply(builder);

    }

    private static void invite(String[] splitter, Message msg){

        msg.reply(DiscordUtils.createReplyEmbed("Pozvánka bota", "Odkaz na pozvání SoftBota na vlastní server\n\n**Link**\n http://softbot.ncodes.eu/discord", "OtherCommand.invite", ReplyEmbedEnum.SUCCESS));

    }

    private static void credits(String[] splitter, Message msg){

        msg.reply(DiscordUtils.createReplyEmbed("Otroci projektu :muscle:",
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
                ReplyEmbedEnum.EASTER_EGG));

    }

    // TODO - Add automatically generated list of BuyMeACoffee sponsors
    private static void sponsors(String[] splitter, Message msg){

        List<String> sponsors = new ArrayList<>();
        String sponsorss = "";

        System.out.println("here");

        Bot.getBot().getServerById("611985124023730185").ifPresent(server -> {
            System.out.println("Server found");
            server.getRoleById("1005966989644271746").ifPresent(role -> {
                System.out.println("Adding " + role.getUsers().size() + " members");
                role.getUsers().forEach(u -> sponsors.add(u.getName()));
            });
        });

        for(String sponsor : sponsors){
            sponsorss += sponsor + "\n";
        }

        msg.reply(DiscordUtils.createReplyEmbed("Sponzoři projektu",
                "**Hlavní sponzoři projektu, díky kterým se mohl SoftBot spustit :heart: **\n" +
                        "Ravenbie (Ravenbie#8833) - 250 Kč\n" +
                        "KiJudo (KiJudo#3946) - 50 Kč\n" +
                        "\n" +
                        "**Sponzoři přes BuyMeACoffee**\n" +
                        sponsorss
                        ,"OtherCommand.sponsors",
                        ReplyEmbedEnum.EASTER_EGG));

    }

    private static void help(String[] splitter, Message msg){
        msg.reply(DiscordUtils.createReplyEmbed("Nápověda", "Nápovědu lze najít na stránkách\n https://softbot.ncodes.eu/wiki/", "OtherCommand.help", ReplyEmbedEnum.SUCCESS));
    }

}
