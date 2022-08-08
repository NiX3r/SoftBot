package Commands;

import Database.DatabaseUtils;
import Enums.ReplyEmbedEnum;
import Instances.GameInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class PendingCommand {

    // Command template
    // !sba pending [game | team | offer | inquiry | shop]
    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        switch (splitter[2]){

            case "game":

                game(event.getMessage());
                break;

            case "team":

                break;

            case "bazaar":

                break;

            case "shop":

                break;

        }

    }

    private static void game(Message msg){

        User user = null;

        if(Bot.getPendingData().getGames().size() == 0){
            msg.reply(DiscordUtils.createReplyEmbed("Žádná data", "Již byla zpracována všechna data, která být zpracována měla. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", ReplyEmbedEnum.WARNING));
            return;
        }

        GameInstance game = Bot.getPendingData().getGames().get(0);
        Bot.getPendingData().getGames().remove(0);

        String repeat_string = "žádné";

        switch (game.getRepeat_date()){
            case "W":
                repeat_string = "týdenní";
                break;
            case "M":
                repeat_string = "měsíční";
                break;
            case "Y":
                repeat_string = "roční";
                break;
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle("Potvrzení registrace hry")
                .setImage(game.getThumbnail() == null ? "" : game.getThumbnail())
                .addField("ID", game.getId() + "")
                .addInlineField("Jméno", game.getName())
                .addInlineField("Začátek akce", DatabaseUtils.encodeDateTime(game.getCreate_date()))
                .addInlineField("Konec akce", DatabaseUtils.encodeDateTime(game.getEnd_date()))
                .addInlineField("Lokace", game.getLocation())
                .addInlineField("Vstupné", String.valueOf(game.getPrice()))
                .addInlineField("Opakování", repeat_string)
                .addInlineField("Typ", game.getType().equals("PB") ? "Plácko bitka" : game.getType())
                .addInlineField("IP adresa", game.getIp_address())
                .addInlineField("Email", game.getEmail())
                .setDescription(game.getDescription())
                .setFooter("Verze: " + Bot.getVersion());

        msg.reply(builder).thenAccept(message_success -> {

            Bot.getPendingData().getCheckingData().put(message_success.getId(), game);
            System.out.println("hgello");
            message_success.addReaction(":white_check_mark:").thenAccept(check_success -> { // TODO - add reaction doesn't work well

                System.out.println("hgelloffddf");
                message_success.addReaction("x").thenAccept(x_success -> {

                    System.out.println("hgellofdfdsfdsfdsfds");
                    message_success.addReaction(":wastebasket:").join();

                });

            });

        });

    }

}
