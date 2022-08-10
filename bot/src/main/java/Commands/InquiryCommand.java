package Commands;

import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.BazaarInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.ArrayList;

public class InquiryCommand {

    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        Utils.LogSystem.log(LogTypeEnum.INFO, "inquiry comand catched by '" + event.getMessageAuthor().getName() + "' on server '" + (event.getServer().isPresent() ? event.getServer().get().getName() : "PrivateMessage") + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        switch (splitter[2]){

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
                event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný příkaz.\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                break;

        }

    }

    private static void create(Message message) {
        message.reply(DiscordUtils.createReplyEmbed("Web", "Vytvořit poptávku lze na stránkách\n https://softbot.ncodes.eu/bazaar/", ReplyEmbedEnum.SUCCESS));
    }

    private static void list(String[] splitter, Message msg) {
        if(splitter.length == 4){

            try {

                if(!isNumber(splitter[3])){
                    msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Index, který jsi zadal není ve správném formátu (číslo)\n\nFormát příkazu:\n`!sb inquiry list <index stránky>`", ReplyEmbedEnum.ERROR));
                    return;
                }

                int page = Integer.parseInt(splitter[3]);
                int max_page = Bot.getBazaar().calculateInquiryPages();

                if(page > max_page){
                    msg.reply(DiscordUtils.createReplyEmbed("Přečíslování stránky", "Stránka, kterou jsi zadal, je moc velká. Maximální stránka je `" + max_page + "`", ReplyEmbedEnum.ERROR));
                    return;
                }

                ArrayList<BazaarInstance> offersAtPage = Bot.getBazaar().getInquiryArrayByPageIndex(page);
                String message = "";

                for(BazaarInstance offer : offersAtPage){

                    message += "\n**ID:** _" + offer.getId() + "_ | **Název:** _" + offer.getName() + "_ | **Cena:** _" + offer.getPrice() + "_ | **PSČ:** _" + offer.getZip() + "_";

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
        if(isNumber(splitter[3])){

            BazaarInstance offer = Bot.getBazaar().getInquiryById(Integer.parseInt(splitter[3]));

            if(offer == null){

                msg.reply(DiscordUtils.createReplyEmbed("Neexistující poptávka", "Nabídka s tímto ID neexistuje. Prosím vyplňte skutečné ID.", ReplyEmbedEnum.ERROR));
                return;

            }

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(Color.decode("#D1A841"))
                    .setTitle("Poptávám: " + offer.getName())
                    .addInlineField("Cena", String.valueOf(offer.getPrice()))
                    .addInlineField("PSČ", String.valueOf(offer.getZip()))
                    .addInlineField("Kontakt", offer.getCreator_ping())
                    .setDescription(offer.getDescription())
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

}
