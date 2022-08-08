package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.ServerOptionInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class AdminCommand {

    public static void run(MessageCreateEvent event){

        event.getServer().ifPresent(server -> {

            if(!Bot.isUserAdmin(event.getMessageAuthor().getId())){
                event.getMessage().reply(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro Admin tým SoftBota", ReplyEmbedEnum.WARNING));
                return;
            }

            Utils.LogSystem.log(LogTypeEnum.INFO, "admin comand catched by '" + event.getMessageAuthor().getName() + "' on server '" + server.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            String[] splitter = event.getMessage().getContent().split(" ");

            switch (splitter[1]){

                case "ban":
                    ban(splitter, event.getMessage());
                    break;

                case "unban":
                    unban(splitter, event.getMessage());
                    break;

                case "server":
                    server(event);
                    break;

                case "announcement":
                    announcement(splitter, event.getMessage());
                    break;

                case "pending":
                    PendingCommand.run(event);
                    break;

                default:
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + "` .\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                    break;

            }

        });

    }

    private static void announcement(String[] splitter, Message message) {

        String content = message.getContent().replace("!sba announcement ", "").replaceAll("<n>", "\n");

        Bot.getBot().getServers().forEach(server -> {

            ServerOptionInstance option = Bot.getServerOption(server.getId());

            if(option == null){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    channel.sendMessage(DiscordUtils.createAnnouncementEmbed(content + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `!sb channel <označení místnosti>`", message.getAuthor()));
                    break;
                }
            }
            else {
                long announcement_channel_id = option.getAnnouncement_channel_id();

                server.getTextChannelById(announcement_channel_id).ifPresent(channel -> {
                    channel.sendMessage(DiscordUtils.createAnnouncementEmbed(content, message.getAuthor()));
                });
            }

        });

    }

    private static void unban(String[] splitter, Message message) {
    }

    private static void server(MessageCreateEvent event) {
        ServerCommand.run(event);
    }

    private static void ban(String[] splitter, Message message) {
    }

}
