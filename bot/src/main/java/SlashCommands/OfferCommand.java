package SlashCommands;

import Database.BazaarUtils;
import Enums.BazaarStatusEnum;
import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.BazaarInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import Utils.FileUtils;
import org.javacord.api.entity.Attachment;
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
            case "sold":
                sold(interaction);
                break;

        }

    }

    private static void sold(SlashCommandInteraction interaction) {

        BazaarInstance offer = Bot.getBazaar().getOfferById(Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString()));

        if(offer == null){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující nabídka", "Nabídka s tímto ID neexistuje. Prosím vyplňte skutečné ID.", "OfferCommand.sold", ReplyEmbedEnum.ERROR)).respond().join();
            return;
        }
        if(offer.getCreator() != interaction.getUser().getId()){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nedostatečná práva", "Bohužel nejsi majitelem této poptávky. Můžeš si vytvořit vlastní na adrese\n https://softbot.ncodes.eu", "OfferCommand.sold", ReplyEmbedEnum.ERROR)).respond().join();
            return;
        }

        offer.setStatus(BazaarStatusEnum.SOLD);
        BazaarUtils.updateBazaarStatus(offer.getId(), BazaarStatusEnum.SOLD, success -> {
            if(success){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Prodáno", "Úspěšně jsi nastavil nabídku jako prodanou.", "OfferCommand.sold", ReplyEmbedEnum.SUCCESS)).respond().join();
            }
            else {
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("", "Nastala chyba při ukládání do databáze. Zkuste to prosím později", "OfferCommand.sold", ReplyEmbedEnum.APP_ERROR)).respond().join();
            }
        });

    }

    private static void show(SlashCommandInteraction interaction) {
        BazaarInstance offer = Bot.getBazaar().getOfferById(Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString()));

        if(offer == null){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující nabídka", "Nabídka s tímto ID neexistuje. Prosím vyplňte skutečné ID.", "OfferCommand.show", ReplyEmbedEnum.ERROR)).respond().join();
            return;
        }

        MessageBuilder messageBuilder = new MessageBuilder();
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle("Nabízím: " + offer.getName())
                .addInlineField("Cena", String.valueOf(offer.getPrice()))
                .addInlineField("PSČ", String.valueOf(offer.getZip()))
                .addInlineField("Kontakt", DiscordUtils.getNickPingById(offer.getUser_id()))
                .setDescription(offer.getDescription())
                .setFooter("Zdroj: OfferCommand.show | Verze: " + Bot.getVersion());

        messageBuilder.setEmbed(builder);
        FileUtils.loadAttachments(offer.getId(), "offer", files -> {

            for(File file : files){
                messageBuilder.addAttachment(file);
            }
            messageBuilder.send(interaction.getChannel().get()).join();

        });

        interaction.createImmediateResponder().setContent("zobrazeno").respond().join();

        return;
    }

    private static void file(SlashCommandInteraction interaction) {
        int id = Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString());
        BazaarInstance instance = Bot.getBazaar().getOfferById(id);

        if(instance == null){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující nabídka", "Nabídka s tímto ID neexistuje. Prosím zadejte správné ID.", "OfferCommand.file", ReplyEmbedEnum.WARNING)).respond().join();
            return;
        }

        if(instance.getUser_id() == interaction.getUser().getId()){
            Attachment attachment = interaction.getArguments().get(1).getAttachmentValue().get();

            if(attachment.getSize() >= 5000000){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Veliký soubor", "Soubor je moc velký. Prosím pošlete v maximální velikosti do 5MB.", "OfferCommand.file", ReplyEmbedEnum.WARNING)).respond().join();
                return;
            }

            FileUtils.saveAttachments(attachment, "offer", id, success -> {

                if (success)
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Uloženo", "Všechny soubory byli úspěšně uloženy", "OfferCommand.file", ReplyEmbedEnum.SUCCESS)).respond().join();

                else
                    interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("", "Nastala chyba aplikce, prosím kontaktujte vývojáře aplikace", "OfferCommand.file", ReplyEmbedEnum.APP_ERROR)).respond().join();

            });
        }
        else {
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Autorizace", "Nejste autorem dané nabídky. Pokud si přejete vytvořit vlastní napište příkaz `/offer create`", "OfferCommand.file", ReplyEmbedEnum.ERROR)).respond().join();
        }
    }

    private static void list(SlashCommandInteraction interaction) {
        try {

            final int max_page = Bot.getBazaar().calculateOfferPages();

            if(interaction.getArguments().size() == 0){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Počet stránek", "Maxímální počet stránek pro listování nabídek je " + max_page, "OfferCommand.list", ReplyEmbedEnum.SUCCESS)).respond().join();
                return;
            }

            int page = Integer.parseInt(interaction.getArguments().get(0).getLongValue().get().toString());

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
