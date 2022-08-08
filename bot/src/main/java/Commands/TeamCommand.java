package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.TeamInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.ArrayList;

public class TeamCommand {

    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        Utils.LogSystem.log(LogTypeEnum.INFO, "team comand catched by '" + event.getMessageAuthor().getName() + "' on server '" + (event.getServer().isPresent() ? event.getServer().get().getName() : "PrivateMessage") + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        switch (splitter[2]){

            case "list":
                list(splitter, event.getMessage());
                break;

            case "show":
                show(splitter, event.getMessage());
                break;

            default:
                event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný příkaz.\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                break;

        }

    }

    private static void list(String[] splitter, Message msg) {

        if(splitter.length == 4){

            try {

                if(!isNumber(splitter[3])){
                    msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Index, který jsi zadal není ve správném formátu (číslo)\n\nFormát příkazu:\n`!sb team list <index stránky>`", ReplyEmbedEnum.ERROR));
                    return;
                }

                int page = Integer.parseInt(splitter[3]);
                int max_page = Bot.getTeamUtil().calculateTeamPages();

                if(page > max_page){
                    msg.reply(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", ReplyEmbedEnum.ERROR));
                    return;
                }

                ArrayList<TeamInstance> teamsInPage = Bot.getTeamUtil().getTeamArrayByPageIndex(page);
                String message = "";

                for(TeamInstance team : teamsInPage){

                    String type = "error";

                    switch (team.getType()){

                        case "CQB&MS":
                            type = "CQB i MilSim";
                            break;

                        case "None":
                            type = "žádný";
                            break;

                        default:
                            type = team.getType();
                            break;

                    }

                    message += "\n**ID:** _" + team.getId() + "_ | **Název:** _" + team.getName() + "_ | **Typ:** _" + type + "_ | **Počet členů:** _" + (team.getMember_count() == -1 ? "nenastaveno" : team.getMember_count()) + "_";

                }

                msg.reply(DiscordUtils.createReplyEmbed("stránka " + page + "/" + max_page, message, ReplyEmbedEnum.SUCCESS));

            }
            catch (Exception ex){

                Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                msg.reply(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", ReplyEmbedEnum.APP_ERROR));

            }

        }

    }

    private static void show(String[] splitter, Message msg) {

        if(splitter.length == 4){
            if(isNumber(splitter[3])){

                TeamInstance team = Bot.getTeamUtil().getTeamById(Integer.parseInt(splitter[3]));

                if(team == null){

                    msg.reply(DiscordUtils.createReplyEmbed("Neexistující tým", "Hra s tímto ID neexistuje. Prosím vyplňte skutečné ID.", ReplyEmbedEnum.ERROR));
                    return;

                }

                String type = "error";

                switch (team.getType()){

                    case "CQB&MS":
                        type = "CQB i MilSim";
                        break;

                    case "None":
                        type = "žádný";
                        break;

                    default:
                        type = team.getType();
                        break;

                }

                System.out.println("'" + team.getWebsite() + "'");

                EmbedBuilder builder = new EmbedBuilder()
                        .setColor(Color.decode("#D1A841"))
                        .setTitle(team.getName())
                        .setImage(team.getThumbnail())
                        .addInlineField("ID", team.getId() + "")
                        .addInlineField("Typ", type)
                        .addInlineField("Počet členů", team.getMember_count() == -1 ? "nenastaveno" : team.getMember_count() + "")
                        .addField("Webové stránky", (team.getWebsite().equals("") ? "nenastaveny" : team.getWebsite()))
                        .setDescription(team.getDescription())
                        .setFooter("Verze: " + Bot.getVersion());

                msg.reply(builder);
                return;

            }
            msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadané ID není číslo. Prosím zadej číslo.", ReplyEmbedEnum.ERROR));
        }

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

}
