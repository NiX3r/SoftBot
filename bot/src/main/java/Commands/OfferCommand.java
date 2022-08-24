package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.BazaarInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import Utils.FileUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OfferCommand {

    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        Utils.LogSystem.log(LogTypeEnum.INFO, "offer comand catched by '" + event.getMessageAuthor().getName() + "' on server '" + (event.getServer().isPresent() ? event.getServer().get().getName() : "PrivateMessage") + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        switch (splitter[2]){

            case "file":
                file(splitter, event.getMessage());
                break;

            case "list":
                list(splitter, event.getMessage());
                break;

            case "show":
                show(splitter, event.getMessage());
                break;

            case "create":
                create(event.getMessage());
                break;

            default:
                event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný příkaz.\n\nPro nápovědu\n`!sb help`", "OfferCommand.run", ReplyEmbedEnum.ERROR));
                break;

        }

    }

    private static void file(String[] splitter, Message msg) {
        if(splitter.length == 5){

            if(msg.getAttachments().size() > 0){
                try {

                    if(!isNumber(splitter[3])){
                        msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Index, který jsi zadal není ve správném formátu (číslo)\n\nFormát příkazu:\n`!sb offer list <index stránky>`", "OfferCommand.file", ReplyEmbedEnum.ERROR));
                        return;
                    }

                    int id = Integer.parseInt(splitter[3]);
                    String password = splitter[4];

                    /*if(Bot.getBazaar().isCorrectPassword(id, password)){
                        List<MessageAttachment> attachments = msg.getAttachments();
                        FileUtils.saveAttachments(attachments, "offer", id, success -> {

                            if (success)
                                msg.reply(DiscordUtils.createReplyEmbed("Uloženo", "Všechny soubory byli úspěšně uloženy", "OfferCommand.file", ReplyEmbedEnum.SUCCESS));

                            else
                                msg.reply(DiscordUtils.createReplyEmbed("", "Nastala chyba aplikce, prosím kontaktujte vývojáře aplikace", "OfferCommand.file", ReplyEmbedEnum.APP_ERROR));

                        });
                    }
                    else {
                        msg.reply(DiscordUtils.createReplyEmbed("Autorizace", "Uvedené ID a heslo neexistují, Prosím zkuste to později", "OfferCommand.file", ReplyEmbedEnum.ERROR));
                    }*/

                }
                catch (Exception ex){

                    Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                    msg.reply(DiscordUtils.createReplyEmbed("", "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", "OfferCommand.file", ReplyEmbedEnum.APP_ERROR));

                }
            }
            else {
                msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zpráva musí obsahovat přiložené soubory\n\nFormát příkazu:\n`!sb inquiry file <id> <heslo pro editaci>`", "OfferCommand.file", ReplyEmbedEnum.ERROR));
                return;
            }
        }
        else {
            msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu.\n\nFormát příkazu\n`!sb inquiry file <id> <heslo>`", "OfferCommand.file", ReplyEmbedEnum.ERROR));
        }
    }
    private static void create(Message message) {
        message.reply(DiscordUtils.createReplyEmbed("Web", "Vytvořit nabídku lze na stránkách\n https://softbot.ncodes.eu/bazaar/", "OfferCommand.create", ReplyEmbedEnum.SUCCESS));
    }

    private static void list(String[] splitter, Message msg) {
        if(splitter.length == 4){

            try {

                if(!isNumber(splitter[3])){
                    msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Index, který jsi zadal není ve správném formátu (číslo)\n\nFormát příkazu:\n`!sb offer list <index stránky>`", "OfferCommand.list", ReplyEmbedEnum.ERROR));
                    return;
                }

                int page = Integer.parseInt(splitter[3]);
                int max_page = Bot.getBazaar().calculateOfferPages();

                if(page > max_page){
                    msg.reply(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", "OfferCommand.list", ReplyEmbedEnum.ERROR));
                    return;
                }

                ArrayList<BazaarInstance> offersAtPage = Bot.getBazaar().getOfferArrayByPageIndex(page);
                String message = "";

                for(BazaarInstance offer : offersAtPage){

                    message += "\n**ID:** _" + offer.getId() + "_ | **Název:** _" + offer.getName() + "_ | **Cena:** _" + offer.getPrice() + "_ | **PSČ:** _" + offer.getZip() + "_";

                }

                msg.reply(DiscordUtils.createReplyEmbed("stránka " + page + "/" + max_page, message, "OfferCommand.list", ReplyEmbedEnum.SUCCESS));

            }
            catch (Exception ex){

                Utils.LogSystem.log(LogTypeEnum.ERROR, "Error: " + ex, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                msg.reply(DiscordUtils.createReplyEmbed(null, "Nastala chyba aplikace. Prosím upozorněte na tuto chybu správce aplikace.\n\nChybová hláška\n`" + ex + "`", "OfferCommand.list", ReplyEmbedEnum.APP_ERROR));

            }

        }
        else if(splitter.length == 3){
            msg.reply(DiscordUtils.createReplyEmbed("Počet stran", "Maximální počet stran je od 1 do " + Bot.getBazaar().calculateOfferPages(), "OfferCommand.list", ReplyEmbedEnum.SUCCESS));
        }
        else {
            msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. \n\nFormát příkazu\n`!sb offer list <index>`", "OfferCommand.list", ReplyEmbedEnum.ERROR));
        }
    }

    private static void show(String[] splitter, Message msg) {
        if(isNumber(splitter[3])){

            BazaarInstance offer = Bot.getBazaar().getOfferById(Integer.parseInt(splitter[3]));

            if(offer == null){

                msg.reply(DiscordUtils.createReplyEmbed("Neexistující nabídka", "Nabídka s tímto ID neexistuje. Prosím vyplňte skutečné ID.", "OfferCommand.show", ReplyEmbedEnum.ERROR));
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
            FileUtils.loadFiles(offer.getId(), "offer", files -> {

                for(File file : files){
                    messageBuilder.addAttachment(file);
                }
                messageBuilder.send(msg.getChannel());

            });

            return;

        }
        msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadané ID není číslo. Prosím zadej číslo.", "OfferCommand.show", ReplyEmbedEnum.ERROR));
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
