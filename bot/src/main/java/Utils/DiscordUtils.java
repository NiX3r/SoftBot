package Utils;

import Enums.ReplyEmbedEnum;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class DiscordUtils {

    public static EmbedBuilder createReplyEmbed(String replyTopic, String replyMessage, ReplyEmbedEnum replyEmbedEnum){

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

        }

        return new EmbedBuilder()
                .setTitle(replyEnum + (replyTopic == null ? "" : " - " + replyTopic))
                .setColor(c)
                .setDescription(replyMessage);

    }

}
