package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import Utils.UTFCorrectionTranslator;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class GameCommand {

    public static void run(MessageCreateEvent event){

        event.getServer().ifPresent(server -> {

            Utils.LogSystem.log(LogTypeEnum.INFO, "game comand catched by '" + event.getMessageAuthor().getName() + "' on server '" + server.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            String[] splitter = event.getMessage().getContent().split(" ");

            switch (splitter[2]){

                case "list":
                    list(splitter, event.getMessage());
                    break;

                case "to":
                    to(splitter, event.getMessage());
                    break;

                case "show":
                    show(splitter, event.getMessage());
                    break;

                default:
                    event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + "` .\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                    break;

            }

        });

    }

    private static void list(String[] splitter, Message msg){

        if(splitter.length == 4){

            try {

                if(!isNumber(splitter[3])){
                    msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Index, který jsi zadal není ve správném formátu (číslo)\n\nFormát příkazu:\n`!sb game list <index stránky>`", ReplyEmbedEnum.ERROR));
                    return;
                }

                int page = Integer.parseInt(splitter[3]);
                int max_page = Bot.getCalendar().calculateGamePages();

                if(page > max_page){
                    msg.reply(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", ReplyEmbedEnum.ERROR));
                    return;
                }

                ArrayList<GameInstance> gamesInPage = Bot.getCalendar().getGameArrayByPageIndex(page);
                String message = "";

                for(GameInstance game : gamesInPage){

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

                    message += "\n**ID:** _" + game.getId() + "_ | **Název:** _" + game.getName() + "_ | **Typ:** _" + (game.getType().equals("PB") ? "Plácko bitka" : game.getType()) + "_ | **Opakování:** _" + repeat_string + "_";

                }

                msg.reply(DiscordUtils.createReplyEmbed("stránka " + page + "/" + max_page, message, ReplyEmbedEnum.SUCCESS));

            }
            catch (Exception ex){

                Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                msg.reply(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", ReplyEmbedEnum.APP_ERROR));

            }

        }

    }

    private static void to(String[] splitter, Message msg){

        if(splitter.length == 4){
            try{

                String message = "Byly nalezeny tyto hry (název, typ, začátek):\n\n";
                ArrayList<CalendarGameInstance> gamesInDate = new ArrayList<CalendarGameInstance>();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date to_date = sdf.parse(splitter[3].replace(".", "-"));
                long to_ms = to_date.getTime();
                long today_ms = System.currentTimeMillis();

                long year_ms = Long.parseLong("31556952000");

                if(to_ms > today_ms){

                    if(to_ms < today_ms + 2 * year_ms){

                        for(int index = 0; index < Bot.getCalendar().getCalendar().size(); index++){

                            if(Bot.getCalendar().getCalendar().get(index).getStart_date() < today_ms)
                                continue;

                            if(Bot.getCalendar().getCalendar().get(index).getEnd_date() > to_ms)
                                break;

                            gamesInDate.add(Bot.getCalendar().getCalendar().get(index));

                        }

                    }
                    else {
                        msg.reply(DiscordUtils.createReplyEmbed("Vysoké datum", "Zadal jsi moc vysoké datum. Kalendář je schopný aktuálně vypočítat akce maximálně dva roky dopředu.", ReplyEmbedEnum.ERROR));
                        return;
                    }

                }
                else{

                    if(to_ms > today_ms - year_ms){

                        for(int index = Bot.getCalendar().getCalendar().size() - 1; index >= 0; index--){

                            if(Bot.getCalendar().getCalendar().get(index).getStart_date() > today_ms)
                                continue;

                            if(Bot.getCalendar().getCalendar().get(index).getEnd_date() < to_ms)
                                break;

                            gamesInDate.add(Bot.getCalendar().getCalendar().get(index));

                        }

                    }
                    else {
                        msg.reply(DiscordUtils.createReplyEmbed("Nízké datum", "Zadal jsi moc nízké datum. Kalendář je schopný aktuálně vypočítat akce maximálně rok nazpět.", ReplyEmbedEnum.ERROR));
                        return;
                    }

                }

                for(CalendarGameInstance game : gamesInDate){

                    GameInstance gi = Bot.getCalendar().getGameById(game.getRoot_game_id());
                    String name = gi.getName();
                    String type = gi.getType();
                    String start_date = getDate(game.getStart_date());
                    String temp_msg = "**" + name + "**, " + type + ", začátek *" + start_date + "*\n";


                    if(message.length() + temp_msg.length() > 1900){

                        message += "A další, které se nevešli do jedné zprávy ...";
                        break;

                    }
                    else {
                        message += temp_msg;
                    }

                }

                msg.reply(DiscordUtils.createReplyEmbed("Nalezený seznam", message, ReplyEmbedEnum.SUCCESS));
                gamesInDate.clear();

            }
            catch (Exception exception){

                Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + exception.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                msg.reply(DiscordUtils.createReplyEmbed("Formát data", "Špatný formát data. Prosím napiš datum ve formátu '21.08.2002' nebo '21-08-2002'", ReplyEmbedEnum.ERROR));

            }
        }
        else {
            msg.reply(DiscordUtils.createReplyEmbed("Formát příkazu", "Zadal jste špatný formát data! \n\nSprávný formát\n `!sb game date <datum>`", ReplyEmbedEnum.ERROR));
        }

    }

    private static void show(String[] splitter, Message msg){

        if(isNumber(splitter[3])){

            GameInstance game = Bot.getCalendar().getGameById(Integer.parseInt(splitter[3]));

            if(game == null){

                msg.reply(DiscordUtils.createReplyEmbed("Neexistující hra", "Hra s tímto ID neexistuje. Prosím vyplňte skutečné ID.", ReplyEmbedEnum.ERROR));
                return;

            }

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
                    .setTitle(game.getName())
                    .setImage(game.getThumbnail() == null ? "" : game.getThumbnail())
                    .addField("ID", game.getId() + "")
                    .addInlineField("Začátek akce", getDate(game.getStart_date()))
                    .addInlineField("Konec akce", getDate(game.getEnd_date()))
                    .addInlineField("Lokace", game.getLocation())
                    .addInlineField("Vstupné", String.valueOf(game.getPrice()))
                    .addInlineField("Opakování", repeat_string)
                    .addInlineField("Typ", game.getType().equals("PB") ? "Plácko bitka" : game.getType())
                    .setDescription(game.getDescription())
                    .setFooter("Verze: " + Bot.getVersion());

            msg.reply(builder);
            return;

        }
        msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadané ID není číslo. Prosím zadej číslo.", ReplyEmbedEnum.ERROR));

    }

    private static boolean isNumber(String value){
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    private static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateString = formatter.format(new Date(milliSeconds));
        return dateString;
    }

}
