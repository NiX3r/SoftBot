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

public class OtherCommand {

    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        Utils.LogSystem.log(LogTypeEnum.INFO, "other comand catched by " + event.getMessageAuthor().getName(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

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

            case "help":
                help(splitter, event.getMessage());
                break;

            case "channel":
                channel(splitter, event.getMessage());
                break;

            case "team-role":
                teamRole(splitter, event.getMessage());
                break;

            default:
                event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný příkaz.\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
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
                                        message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit týmovou roli.", ReplyEmbedEnum.SUCCESS));
                                    }
                                    else{
                                        message.reply(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", ReplyEmbedEnum.APP_ERROR));
                                    }

                                });

                            }
                            else {

                                ServerOptionInstance serverOption = new ServerOptionInstance(server.getId(), -1, message.getMentionedRoles().get(0).getId());
                                Bot.getServerOptions().add(serverOption);
                                ServerOptionUtils.addServerOption(server.getId(), -1, message.getMentionedRoles().get(0).getId(), add_success -> {
                                    Bot.getTeamUtil().getTeamByDiscordId(server.getId()).recalculateMemberCount();
                                    message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit týmovou roli.", ReplyEmbedEnum.SUCCESS));

                                });

                            }

                        });
                    }
                    else {
                        message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Pro správné využítí tohoto příkazu musíš označit roli.\n\nFormát příkazu\n`!sb team-role <označení role>`", ReplyEmbedEnum.WARNING));
                    }
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("Nenastavenost", "Tento server není nastaven jako týmový server. Prosím upravte jej v sekcí týmu", ReplyEmbedEnum.WARNING));
                }
            }
            else {
                message.reply(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro administrátory tohoto serveru", ReplyEmbedEnum.WARNING));
            }
        }
        else {
            message.reply(DiscordUtils.createReplyEmbed("Lokace příkazu", "Tento příkaz lze vykonat pouze na serveru", ReplyEmbedEnum.WARNING));
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
                                    message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál.", ReplyEmbedEnum.SUCCESS));
                                }
                                else{
                                    message.reply(DiscordUtils.createReplyEmbed("Chyba", "Nastala chyba, prosím kontaktujte správce SoftBota", ReplyEmbedEnum.APP_ERROR));
                                }

                            });

                        }
                        else {

                            ServerOptionInstance serverOption = new ServerOptionInstance(server.getId(), message.getMentionedChannels().get(0).getId(), -1);
                            Bot.getServerOptions().add(serverOption);
                            ServerOptionUtils.addServerOption(server.getId(), message.getMentionedChannels().get(0).getId(), -1, add_success -> {

                                message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Podařilo se upravit oznamovací kanál.", ReplyEmbedEnum.SUCCESS));

                            });

                        }

                    });
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Pro správné využítí tohoto příkazu musíš označit kanál.\n\nFormát příkazu\n`!sb channel <označení kanálu>`", ReplyEmbedEnum.WARNING));
                }

            }
            else {
                message.reply(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro administrátory tohoto serveru", ReplyEmbedEnum.WARNING));
            }
        }
        else {
            message.reply(DiscordUtils.createReplyEmbed("Lokace příkazu", "Tento příkaz lze vykonat pouze na serveru", ReplyEmbedEnum.WARNING));
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
                .setFooter("Verze: " + Bot.getVersion());

        msg.reply(builder);

    }

    private static void invite(String[] splitter, Message msg){

        msg.reply(DiscordUtils.createReplyEmbed("Pozvánka bota", "Odkaz na pozvání SoftBota na vlastní server\n\n**Link**\n http://softbot.ncodes.eu/discord", ReplyEmbedEnum.SUCCESS));

    }

    private static void credits(String[] splitter, Message msg){

        msg.reply(DiscordUtils.createReplyEmbed("Otroci projektu",
                "**Vývojářský tým**\n" +
                "NiX3r (NiX3r#0272) - _hlavní vývojář, back-end Discord bot_\n" +
                "Orim0 (Orim0#7985) - _web vývojář, back-end web services_\n" +
                "KiJudo (KiJudo#3946) - _designérka, design web UI_\n" +
                "\n" +
                "**Testeři**\n" +
                "Kykrovec (Kykrobie#4976) - _tester, testování v beta verzi_", ReplyEmbedEnum.EASTER_EGG));

    }

    private static void help(String[] splitter, Message msg){

    }

}
