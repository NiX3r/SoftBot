package SlashCommands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.TeamInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.ArrayList;

public class TeamCommand {

    public static void run(SlashCommandInteraction interaction){

        switch (interaction.getOptions().get(0).getName()){

            case "create":
                create(interaction);
                break;
            case "list":
                list(interaction);
                break;
            case "invite":
                invite(interaction);
                break;
            case "show":
                show(interaction);
                break;

        }

    }

    private static void invite(SlashCommandInteraction interaction) {
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "TeamCommands.run", ReplyEmbedEnum.WARNING));
    }

    private static void show(SlashCommandInteraction interaction) {
        try{
            TeamInstance team = Bot.getTeamUtil().getTeamById(Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString()));

            if(team == null){

                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující tým", "Hra s tímto ID neexistuje. Prosím vyplňte skutečné ID.", "TeamCommand.show", ReplyEmbedEnum.ERROR)).respond().join();
                return;

            }

            String type;

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

            interaction.createImmediateResponder().addEmbed(builder).respond().join();
            return;
        }
        catch (Exception exception){
            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + exception.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Formát data", "Špatný formát data. Prosím napiš datum ve formátu '21.08.2002' nebo '21-08-2002'", "TeamsCommands.show", ReplyEmbedEnum.ERROR)).respond().join();
        }
    }

    private static void list(SlashCommandInteraction interaction) {
        try {

            final int max_page = Bot.getTeamUtil().calculateTeamPages();

            if(interaction.getArguments().size() == 0){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Počet stránek", "Maxímální počet stránek pro listování týmů je " + max_page, "TeamCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();
                return;
            }

            int page = Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString());

            if(page > max_page){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", "TeamCommand.list", ReplyEmbedEnum.ERROR)).respond().join();
                return;
            }

            ArrayList<TeamInstance> teamsInPage = Bot.getTeamUtil().getTeamArrayByPageIndex(page);
            String message = "";

            for(TeamInstance team : teamsInPage){

                String type;

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

            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("stránka " + page + "/" + max_page, message, "TeamCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();

        }
        catch (Exception ex){

            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", "TeamCommand.list", ReplyEmbedEnum.APP_ERROR)).respond().join();

        }
    }

    private static void create(SlashCommandInteraction interaction) {
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Web", "Vytvořit tým lze na stránkách\n https://softbot.ncodes.eu/team/", "TeamCommand.create", ReplyEmbedEnum.SUCCESS)).respond().join();
    }

}
