package Commands;

import Enums.ReplyEmbedEnum;
import Instances.RedditPostInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class OtherCommands {

    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

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

        }

    }

    private static void reddit(String[] splitter, Message msg){

        RedditPostInstance post = Bot.getReddit().getRandomPost();

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle(post.getTopic())
                .addField("Autor", post.getAuthor())
                .addField("Odkaz", "https://reddit.com" + post.getLink())
                .setImage(post.getContent());

        msg.reply(builder);

    }

    private static void invite(String[] splitter, Message msg){

        msg.reply(DiscordUtils.createReplyEmbed("Pozvánka bota", "Odkaz na pozvání SoftBota na vlastní server\n\n**Link**\n https://www.softbot.ncodes.eu/discord", ReplyEmbedEnum.SUCCESS));

    }

    private static void credits(String[] splitter, Message msg){

        msg.reply(DiscordUtils.createReplyEmbed("Otroci projektu",
                "**Vývojářský tým**\n" +
                "NiX3r (NiX3r#0272) - _hlavní vývojář, back-end Discord bot_\n" +
                "Orim0 (Orim0#7985) - _web vývojář, back-end web services_\n" +
                "KiJudo (KiJudo#3946) - _designérka, design web UI_\n" +
                "\n" +
                "**Testeři**\n" +
                "Kykrobie (Kykrobie#4976) - _tester, testování v beta verzi_", ReplyEmbedEnum.EASTER_EGG));

    }

    private static void help(String[] splitter, Message msg){

    }

}
