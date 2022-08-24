package SlashCommands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.GameInstance;
import Instances.ShopInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import Utils.UTFCorrectionTranslator;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.ArrayList;

public class ShopCommand {

    public static void run(SlashCommandInteraction interaction){

        switch (interaction.getOptions().get(0).getName()){

            case "create":
                create(interaction);
                break;
            case "list":
                list(interaction);
                break;
            case "at":
                at(interaction);
                break;
            case "show":
                show(interaction);
                break;

        }

    }

    private static void list(SlashCommandInteraction interaction) {
        try {
            int page = Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString());
            int max_page = Bot.getShop().calculateShopPages();

            if(page > max_page){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", "ShopCommand.list", ReplyEmbedEnum.WARNING)).respond().join();
                return;
            }

            ArrayList<ShopInstance> gamesInPage = Bot.getShop().getGameArrayByPageIndex(page);
            String message = "";

            for(ShopInstance game : gamesInPage){

                message += "\n**ID:** _" + game.getId() + "_ | **Název:** _" + game.getName() + "_ | **Slevový kód:** _" + (game.getVoucher().equals("") ? "nenastaven" : game.getVoucher()) + "_";

            }

            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("stránka " + page + "/" + max_page, message, "ShopCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();

        }
        catch (Exception ex){

            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", "ShopCommand.list", ReplyEmbedEnum.APP_ERROR)).respond().join();

        }
    }

    private static void at(SlashCommandInteraction interaction) {
        try {

            int zip = Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString());
            String message = "";

            for(ShopInstance game : Bot.getShop().getShops()){

                if(game.getZip() == zip)
                    message += "\n**ID:** _" + game.getId() + "_ | **Název:** _" + game.getName() + "_ | **Slevový kód:** _" + (game.getVoucher().equals("") ? "nenastaven" : game.getVoucher()) + "_";

            }

            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nalezené obchody", message, "ShopCommand.at", ReplyEmbedEnum.SUCCESS)).respond().join();

        }
        catch (Exception ex){

            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", "ShopCommand.at", ReplyEmbedEnum.APP_ERROR)).respond().join();

        }
    }

    private static void show(SlashCommandInteraction interaction) {
        try{
            ShopInstance game = Bot.getShop().getShopById(Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString()));

            if(game == null){

                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující obchod", "Obchod s tímto ID neexistuje. Prosím vyplňte skutečné ID.", "ShopCommand.show", ReplyEmbedEnum.ERROR)).respond().join();
                return;

            }

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(Color.decode("#D1A841"))
                    .setTitle(game.getName())
                    .setImage(game.getThumbnail() == null ? "" : game.getThumbnail())
                    .addInlineField("ID", game.getId() + "")
                    .addInlineField("Slevový kód", game.getVoucher())
                    .addInlineField("Web", game.getWebsite())
                    .addInlineField("Adresa", game.getLocation().equals("") ? "nenastavena" : UTFCorrectionTranslator.translate(game.getLocation()))
                    .addInlineField("PSČ", String.valueOf(game.getZip()))
                    .setDescription(game.getDescription())
                    .setFooter("Verze: " + Bot.getVersion());

            interaction.createImmediateResponder().addEmbed(builder).respond().join();
            return;
        }
        catch (Exception exception){
            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + exception.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Formát data", "Špatný formát data. Prosím napiš datum ve formátu '21.08.2002' nebo '21-08-2002'", "ShopCommand.show", ReplyEmbedEnum.ERROR)).respond().join();
        }
    }

    private static void create(SlashCommandInteraction interaction) {
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Web", "Vytvořit obchod lze na stránkách\n https://softbot.ncodes.eu/shop/", "ShopCommand.create", ReplyEmbedEnum.SUCCESS)).respond().join();
    }

}
