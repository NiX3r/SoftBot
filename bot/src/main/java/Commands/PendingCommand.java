package Commands;

import Database.DatabaseUtils;
import Enums.ReplyEmbedEnum;
import Instances.GameInstance;
import Instances.TeamInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.Arrays;

public class PendingCommand {

    // Command template
    // !sba pending [game | team | offer | inquiry | shop]
    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        switch (splitter[2]){

            case "game":

                game(event.getMessage(), event.getMessageAuthor().getId());
                break;

            case "team":

                team(event.getMessage(), event.getMessageAuthor().getId());
                break;

            case "bazaar":

                break;

            case "shop":

                break;

        }

    }

    private static void team(Message msg, long user_id) {
        if(Bot.getPendingData().getTeams().size() == 0){
            msg.reply(DiscordUtils.createReplyEmbed("Žádná data", "Již byla zpracována všechna data, která být zpracována měla. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", ReplyEmbedEnum.WARNING));
            return;
        }

        TeamInstance team = Bot.getPendingData().getTeams().get(0);
        Bot.getPendingData().getTeams().remove(0);

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle("Potvrzení registrace hry")
                .setImage(team.getThumbnail() == null ? "" : team.getThumbnail())
                .addInlineField("ID", team.getId() + "")
                .addInlineField("Jméno", team.getName())
                .addInlineField("Email", team.getEmail())
                .addInlineField("IP adresa", team.getIp_address())
                .addInlineField("Web", team.getWebsite())
                .addInlineField("Typ", team.getType().replace("CQB&MS", "CQB a MilSim"))
                .addInlineField("Discord Server ID", team.getDiscord_server_id() + "")
                .setDescription(team.getDescription())
                .setFooter("Verze: " + Bot.getVersion());

        MessageBuilder msg_builder = new MessageBuilder()
                .setEmbed(builder)
                .addComponents(
                        ActionRow.of(Button.success("ncodes-softbot-pending-team-approve-" + user_id, "Povolit"),
                                Button.danger("ncodes-softbot-pending-team-deny-" + user_id, "Zakákat"),
                                Button.danger("ncodes-softbot-pending-team-remove-" + user_id, "Smazat"))
                );

        msg_builder.send(msg.getChannel()).thenAccept(success -> {

            Bot.getPendingData().getCheckingData().put(user_id, team);

        });
    }

    private static void game(Message msg, long user_id){

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
                .addInlineField("Začátek akce", DatabaseUtils.encodeDateTime(game.getStart_date()))
                .addInlineField("Konec akce", DatabaseUtils.encodeDateTime(game.getEnd_date()))
                .addInlineField("Lokace", game.getLocation())
                .addInlineField("Vstupné", String.valueOf(game.getPrice()))
                .addInlineField("Opakování", repeat_string)
                .addInlineField("Typ", game.getType().equals("PB") ? "Plácko bitka" : game.getType())
                .addInlineField("IP adresa", game.getIp_address())
                .addInlineField("Email", game.getEmail())
                .setDescription(game.getDescription())
                .setFooter("Verze: " + Bot.getVersion());

        MessageBuilder msg_builder = new MessageBuilder()
                .setEmbed(builder)
                .addComponents(
                        ActionRow.of(Button.success("ncodes-softbot-pending-game-approve-" + user_id, "Povolit"),
                                Button.danger("ncodes-softbot-pending-game-deny-" + user_id, "Zakákat"),
                                Button.danger("ncodes-softbot-pending-game-remove-" + user_id, "Smazat"))
                );

        msg_builder.send(msg.getChannel()).thenAccept(success -> {

            Bot.getPendingData().getCheckingData().put(user_id, game);

        });

    }

}
