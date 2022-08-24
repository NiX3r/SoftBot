package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

public class ServerCommand {

    public static void run(MessageCreateEvent event){

        event.getServer().ifPresent(server -> {

            Utils.LogSystem.log(LogTypeEnum.INFO, "server comand catched by " + event.getMessageAuthor().getName() + "' on server '" + event.getServer().get().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            String[] splitter = event.getMessage().getContent().split(" ");

            switch (splitter[2]){

                case "list":
                    list(splitter, event.getMessage());
                    break;

                case "link":
                    link(splitter, event.getMessage());
                    break;

                case "disconnect":
                    disconnect(splitter, event.getMessage());
                    break;

                case "ban":
                    ban(splitter, event.getMessage());
                    break;

                case "unban":
                    unban(splitter, event.getMessage());
                    break;

                default:
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + "` .\n\nPro nápovědu\n`!sb help`", "ServerCommand.run", ReplyEmbedEnum.ERROR));
                    break;

            }

        });

    }

    private static void ban(String[] splitter, Message message) {
    }

    private static void disconnect(String[] splitter, Message message) {
        if(splitter.length == 4){
            long server_id = valueOf(splitter[3]);
            if(server_id == -1){
                message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát server ID. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + " " + splitter[2] + "` .\n\nPro nápovědu\n`!sb help`", "ServerCommand.disconnect", ReplyEmbedEnum.ERROR));
                return;
            }
            if(Bot.getBot().getServerById(server_id).isPresent()){
                Bot.getBot().getServerById(server_id).ifPresent(server -> {
                    server.leave().thenAccept(success_leave -> {
                        message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi odpojil SoftBota od serveru '" + server.getName() +"'", "ServerCommand.disconnect", ReplyEmbedEnum.SUCCESS));
                    });
                });
            }
            else {
                message.reply(DiscordUtils.createReplyEmbed("Neexistující ID", "Zadal jsi neexistující ID serveru. Nebo na tom serveru není připojený SoftBot.`", "ServerCommand.disconnect", ReplyEmbedEnum.ERROR));
                return;
            }
        }
        else {
            message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + " " + splitter[2] + "` .\n\nPro nápovědu\n`!sb help`", "ServerCommand.disconnect", ReplyEmbedEnum.ERROR));
            return;
        }
    }

    private static void link(String[] splitter, Message message) {
        if(splitter.length == 4){
            long server_id = valueOf(splitter[3]);
            if(server_id == -1){
                message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát server ID. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + " " + splitter[2] + "` .\n\nPro nápovědu\n`!sb help`", "ServerCommand.link", ReplyEmbedEnum.ERROR));
                return;
            }
            if(Bot.getBot().getServerById(server_id).isPresent()){
                Bot.getBot().getServerById(server_id).ifPresent(server -> {
                    if(server.getSystemChannel().isPresent()){
                        server.getSystemChannel().ifPresent(channel -> {
                            channel.createInviteBuilder().create().thenAccept(finalInvite -> {
                                message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), "ServerCommand.link", ReplyEmbedEnum.SUCCESS));
                            }).exceptionally(err -> {
                                message.reply(DiscordUtils.createReplyEmbed("", "Nastala chyba aplikace. Prosím kontaktuje vývojáře.\n\nChybová hláška\n" + err.getMessage(), "ServerCommand.link", ReplyEmbedEnum.APP_ERROR));
                                return null;
                            });
                        });
                    }
                    else {
                        server.getTextChannels().get(0).createInviteBuilder().create().thenAccept(finalInvite -> {
                            message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), "ServerCommand.link", ReplyEmbedEnum.SUCCESS));
                        });
                        for(ServerTextChannel channel : server.getTextChannels()){
                            if(channel.canSee(server.getEveryoneRole().getUsers().iterator().next())){
                                channel.createInviteBuilder().create().thenAccept(finalInvite -> {
                                    message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), "ServerCommand.link", ReplyEmbedEnum.SUCCESS));
                                }).exceptionally(err -> {
                                    message.reply(DiscordUtils.createReplyEmbed("", "Nastala chyba aplikace. Prosím kontaktuje vývojáře.\n\nChybová hláška\n" + err.getMessage(), "ServerCommand.link", ReplyEmbedEnum.APP_ERROR));
                                    return null;
                                });
                            }
                        }
                    }
                });
            }
            else {
                message.reply(DiscordUtils.createReplyEmbed("Neexistující ID", "Zadal jsi neexistující ID serveru. Nebo na tom serveru není připojený SoftBot.`", "ServerCommand.link", ReplyEmbedEnum.ERROR));
                return;
            }
        }
        else {
            message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + " " + splitter[2] + "` .\n\nPro nápovědu\n`!sb help`", "ServerCommand.link", ReplyEmbedEnum.ERROR));
            return;
        }
    }

    private static void list(String[] splitter, Message message) {

        String msg = "";
        for (Server server : Bot.getBot().getServers()){
            msg += server.getName() + ", ID: " + server.getId() + ", Počet uživatelů: " + server.getMemberCount() + "\n";
        }

        message.reply(DiscordUtils.createReplyEmbed("stránka x/x", msg, "ServerCommand.list", ReplyEmbedEnum.SUCCESS));

    }

    private static void unban(String[] splitter, Message message) {
    }

    private static long valueOf(String value){
        try{
            return Long.parseLong(value);
        }
        catch (Exception ex){
            return -1;
        }
    }

}
