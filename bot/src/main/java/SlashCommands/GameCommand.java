package SlashCommands;

import Database.DatabaseUtils;
import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import Utils.GameUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class GameCommand {

    public static void run(SlashCommandInteraction interaction){

        switch (interaction.getOptions().get(0).getName()){

            case "create":
                create(interaction);
                break;
            case "list":
                list(interaction);
                break;
            case "to":
                to(interaction);
                break;
            case "at":
                at(interaction);
                break;
            case "show":
                show(interaction);
                break;

        }

    }

    private static void at(SlashCommandInteraction interaction) {
        try{
            String message = "Byly nalezeny tyto hry (název akce (id)):\n\n";
            ArrayList<CalendarGameInstance> gamesInDate = new ArrayList<CalendarGameInstance>();
            String[] to_splitter = interaction.getArguments().get(0).getStringValue().get().replace(".", "-").split("-");
            String to_string = to_splitter[2] + "-" + to_splitter[1] + "-" + to_splitter[0] + " 00:00:00";
            long to_ms = DatabaseUtils.decodeDateTime(to_string);
            final long year_ms = Long.parseLong("31557600000");
            final long today_ms = System.currentTimeMillis();

            System.out.println(to_string + " equals " + to_ms);

            if(to_ms < (today_ms - year_ms) || to_ms > (today_ms + (2*year_ms))){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Velké/Malé datum", "Zadané datum je moc velké nebo malé. Prosím zadejte datum v rozmezí jednoho roku na zpět nebo dva roky do předu.", "GameCommand.at", ReplyEmbedEnum.WARNING)).respond().join();
                return;
            }

            message += GameUtils.getListGamesOnSpecificDate(to_ms);

            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nalezený seznam", message, "GameCommand.at", ReplyEmbedEnum.SUCCESS)).respond().join();
            gamesInDate.clear();

        }
        catch (Exception exception){

            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + exception.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Formát data", "Špatný formát data. Prosím napiš datum ve formátu `21.08.2002` nebo `21-08-2002`", "GameCommand.at", ReplyEmbedEnum.ERROR)).respond().join();

        }
    }

    private static void show(SlashCommandInteraction interaction) {
        try{
            GameInstance game = Bot.getCalendar().getGameById(Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString()));

            if(game == null){

                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující hra", "Hra s tímto ID neexistuje. Prosím vyplňte skutečné ID.", "GameCommand.show", ReplyEmbedEnum.ERROR)).respond().join();
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

            interaction.createImmediateResponder().addEmbed(builder).respond().join();
            return;
        }
        catch (Exception exception){
            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + exception.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Formát data", "Špatný formát data. Prosím napiš datum ve formátu '21.08.2002' nebo '21-08-2002'", "GameCommand.show", ReplyEmbedEnum.ERROR)).respond().join();
        }
    }

    private static void to(SlashCommandInteraction interaction) {
        try{
            String message = "Byly nalezeny tyto hry (název, typ, začátek):\n\n";
            ArrayList<CalendarGameInstance> gamesInDate = new ArrayList<CalendarGameInstance>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date to_date = sdf.parse(interaction.getArguments().get(0).getStringValue().get().replace(".", "-"));
            long to_ms = to_date.getTime();
            long today_ms = System.currentTimeMillis();
            final long year_ms = Long.parseLong("31556952000");

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
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Vysoké datum", "Zadal jsi moc vysoké datum. Kalendář je schopný aktuálně vypočítat akce maximálně dva roky dopředu.", "GameCommand.to", ReplyEmbedEnum.ERROR)).respond().join();
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
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nízké datum", "Zadal jsi moc nízké datum. Kalendář je schopný aktuálně vypočítat akce maximálně rok nazpět.", "GameCommand.to", ReplyEmbedEnum.ERROR)).respond().join();
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

            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nalezený seznam", message, "GameCommand.to", ReplyEmbedEnum.SUCCESS)).respond().join();
            gamesInDate.clear();

        }
        catch (Exception exception){

            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + exception.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Formát data", "Špatný formát data. Prosím napiš datum ve formátu '21.08.2002' nebo '21-08-2002'", "GameCommand.to", ReplyEmbedEnum.ERROR)).respond().join();

        }
    }

    private static void list(SlashCommandInteraction interaction) {

        try {

            final int max_page = Bot.getCalendar().calculateGamePages();

            if(interaction.getArguments().size() == 0){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Počet stránek", "Maxímální počet stránek pro listování her je " + max_page, "GameCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();
                return;
            }

            int page = Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString());

            if(page > max_page){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", "GameCommand.list", ReplyEmbedEnum.WARNING)).respond().join();
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

            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("stránka " + page + "/" + max_page, message, "GameCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();

        }
        catch (Exception ex){

            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", "GameCommand.list", ReplyEmbedEnum.APP_ERROR)).respond().join();

        }

    }

    private static void create(SlashCommandInteraction interaction) {
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Web", "Vytvořit hru lze na stránkách\n https://softbot.ncodes.eu/game/", "GameCommand.create", ReplyEmbedEnum.SUCCESS)).respond().join();
    }

    private static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateString = formatter.format(new Date(milliSeconds));
        return dateString;
    }

}
