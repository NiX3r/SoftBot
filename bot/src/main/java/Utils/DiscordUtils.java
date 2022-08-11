package Utils;

import Enums.ReplyEmbedEnum;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;

public class DiscordUtils {

    public static EmbedBuilder createReplyEmbed(String replyTopic, String replyMessage, String author, ReplyEmbedEnum replyEmbedEnum){

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
                .setFooter("Zdroj: " + author + " | Verze: " + Bot.getVersion());

    }

    public static EmbedBuilder createAnnouncementEmbed(String content, MessageAuthor author){

        return new EmbedBuilder()
                .setTitle("Oznamovací systém :ear:")
                .setDescription(content)
                .setColor(Color.decode("#D1A841"))
                .setFooter("Administrátor: " + author.getName() + " | Verze: " + Bot.getVersion(), author.getAvatar().getUrl().toString());

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
