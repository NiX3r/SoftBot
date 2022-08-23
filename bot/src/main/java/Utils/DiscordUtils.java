package Utils;

import Enums.ReplyEmbedEnum;
import Instances.ServerOptionInstance;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.util.function.Consumer;

public class DiscordUtils {

    public static EmbedBuilder createReplyEmbed(String replyTopic, String replyMessage, String source, ReplyEmbedEnum replyEmbedEnum){

        Color c = null;
        String replyEnum = "";

        switch (replyEmbedEnum){

            case SUCCESS:
                c = Color.decode("#37d31f");
                replyEnum = "Úspěšnost";
                break;

            case WARNING:
                c = Color.decode("#d37f1f");
                replyEnum = "Upozornění";
                break;

            case ERROR:
                c = Color.decode("#d31f1f");
                replyEnum = "Chyba";
                break;

            case APP_ERROR:
                c = Color.decode("#1f28d3");
                replyEnum = "Chyba na straně aplikace";
                break;

            case EASTER_EGG:
                c = Color.decode("#7900ff");
                replyEnum = "Easter Egg";
                break;

        }

        return new EmbedBuilder()
                .setTitle(replyEnum + (replyTopic == null ? "" : " - " + replyTopic))
                .setColor(c)
                .setDescription(replyMessage)
                .setFooter("Zdroj: " + source + " | Verze: " + Bot.getVersion());

    }

    public static void sendAnnouncementEmbed(String content, String author, String author_avatar, Consumer<Boolean> callback){

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Oznamovací systém :ear:")
                .setDescription(content)
                .setColor(Color.decode("#D1A841"))
                .setFooter("Administrátor: " + author + " | Verze: " + Bot.getVersion(), author_avatar);

        Bot.getBot().getServers().forEach(server -> {

            ServerOptionInstance option = Bot.getServerOption(server.getId());

            if(option == null){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(content + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `!sb channel <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            else {
                long announcement_channel_id = option.getAnnouncement_channel_id();

                server.getTextChannelById(announcement_channel_id).ifPresent(channel -> {
                    builder.setDescription(content);
                    channel.sendMessage(builder);
                });
            }

        });

        callback.accept(true);

    }

    public static String getNickPingById(long id){
        for(Server server : Bot.getBot().getServers()){
            if(server.getMemberById(id).isPresent()){
                return server.getMemberById(id).get().getMentionTag();
            }
        }
        return null;
    }

}
