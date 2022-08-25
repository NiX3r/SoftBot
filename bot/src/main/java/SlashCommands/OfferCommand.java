package SlashCommands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.BazaarInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import Utils.FileUtils;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OfferCommand {

    public static void run(SlashCommandInteraction interaction){

        switch (interaction.getOptions().get(0).getName()){

            case "create":
                create(interaction);
                break;
            case "list":
                list(interaction);
                break;
            case "file":
                file(interaction);
                break;
            case "show":
                show(interaction);
                break;

        }

    }

    private static void show(SlashCommandInteraction interaction) {
        BazaarInstance offer = Bot.getBazaar().getOfferById(Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString()));

        if(offer == null){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující nabídka", "Nabídka s tímto ID neexistuje. Prosím vyplňte skutečné ID.", "OfferCommand.show", ReplyEmbedEnum.ERROR)).respond().join();
            return;
        }

        System.out.println(offer.getId() + " | " + offer.getPrice() + " | " + offer.getZip() + " | " + offer.getCreator_ping() + " | " + offer.getDescription());

        MessageBuilder messageBuilder = new MessageBuilder();
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle("Nabízím: " + offer.getName())
                .addInlineField("Cena", String.valueOf(offer.getPrice()))
                .addInlineField("PSČ", String.valueOf(offer.getZip()))
                .addInlineField("Kontakt", offer.getCreator_ping().equals("") ? "nenastaven" : offer.getCreator_ping())
                .setDescription(offer.getDescription())
                .setFooter("Zdroj: OfferCommand.show | Verze: " + Bot.getVersion());

        messageBuilder.setEmbed(builder);
        FileUtils.loadAttachments(offer.getId(), "offer", files -> {

            for(File file : files){
                messageBuilder.addAttachment(file);
            }
            messageBuilder.send(interaction.getChannel().get());

        });

        return;
    }

    private static void file(SlashCommandInteraction interaction) {
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "OfferCommands.run", ReplyEmbedEnum.WARNING)).respond().join();
    }

    private static void list(SlashCommandInteraction interaction) {
        try {

            int page = Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString());
            int max_page = Bot.getBazaar().calculateOfferPages();

            if(page > max_page){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", "OfferCommand.list", ReplyEmbedEnum.ERROR)).respond().join();
                return;
            }

            ArrayList<BazaarInstance> offersAtPage = Bot.getBazaar().getOfferArrayByPageIndex(page);
            String message = "";

            for(BazaarInstance offer : offersAtPage){

                message += "\n**ID:** _" + offer.getId() + "_ | **Název:** _" + offer.getName() + "_ | **Cena:** _" + offer.getPrice() + "_ | **PSČ:** _" + offer.getZip() + "_";

            }

            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("stránka " + page + "/" + max_page, message, "OfferCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();

        }
        catch (Exception ex){

            Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", "OfferCommand.list", ReplyEmbedEnum.APP_ERROR)).respond().join();

        }
    }

    private static void create(SlashCommandInteraction interaction) {
        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Web", "Vytvořit nabídku lze na stránkách\n https://softbot.ncodes.eu/bazaar/", "OfferCommand.create", ReplyEmbedEnum.SUCCESS)).respond().join();
    }

}
