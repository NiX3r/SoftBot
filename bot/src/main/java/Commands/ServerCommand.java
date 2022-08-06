package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

public class ServerCommand {

    public static void run(MessageCreateEvent event){

        event.getServer().ifPresent(server -> {

            Utils.LogSystem.log(LogTypeEnum.INFO, "game comand catched by " + event.getMessageAuthor().getName(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

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
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + "` .\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                    break;

            }

        });

    }

    private static void ban(String[] splitter, Message message) {
    }

    private static void disconnect(String[] splitter, Message message) {
    }

    private static void link(String[] splitter, Message message) {
        if(splitter.length == 4){
            long server_id = valueOf(splitter[3]);
            if(server_id == -1){
                message.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát server ID. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + "` .\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                return;
            }
            Bot.getBot().getServerById(server_id).ifPresent(server -> {
                if(server.getSystemChannel().isPresent()){
                    server.getSystemChannel().ifPresent(channel -> {
                        channel.createInviteBuilder().create().thenAccept(finalInvite -> {
                            message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), ReplyEmbedEnum.SUCCESS));
                        });
                    });
                }
                else {
                    server.getTextChannels().get(0).createInviteBuilder().create().thenAccept(finalInvite -> {
                        message.reply(DiscordUtils.createReplyEmbed("Úspěch", "Úspěšně jsi vytvořil invite link na server '" + server.getName() + "'\n\nOdkaz\n" + finalInvite.getUrl(), ReplyEmbedEnum.SUCCESS));
                    });
                }
            });
        }
    }

    private static void list(String[] splitter, Message message) {

        String msg = "";
        for (Server server : Bot.getBot().getServers()){
            msg += server.getName() + ", ID: " + server.getId() + ", Počet uživatelů:" + server.getMemberCount() + "\n";
        }

        message.reply(DiscordUtils.createReplyEmbed("stránka x/x", msg, ReplyEmbedEnum.SUCCESS));

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
