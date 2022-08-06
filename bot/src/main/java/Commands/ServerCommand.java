package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
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
    }

    private static void list(String[] splitter, Message message) {
    }

    private static void unban(String[] splitter, Message message) {
    }

}
