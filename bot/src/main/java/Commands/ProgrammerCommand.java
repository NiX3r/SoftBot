package Commands;

import Database.DeveloperCommandUtils;
import Enums.ReplyEmbedEnum;
import Instances.AdminInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;

public class ProgrammerCommand {

    public static void run(MessageCreateEvent event){

        String[] splitter = event.getMessage().getContent().split(" ");

        if(!event.getMessageAuthor().getIdAsString().equals("397714589548019722")){
            event.getMessage().reply(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro vývojáře SoftBota", ReplyEmbedEnum.WARNING));
            return;
        }

        Utils.LogSystem.log(Bot.getPrefix(), "programmer comand catched by " + event.getMessageAuthor().getName(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        switch (splitter[1]){

            case "admin":

                switch (splitter[2]){

                    case "add":
                        add(splitter, event.getMessage());
                        break;

                    case "remove":
                        remove(splitter, event.getMessage());
                        break;

                    default:
                        event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný podpříkaz sekce `" + splitter[1]  + "` .\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                        break;

                }
                break;

            default:
                event.getMessage().reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Prosím zadej správný příkaz.\n\nPro nápovědu\n`!sb help`", ReplyEmbedEnum.ERROR));
                break;


        }

    }

    private static void add(String[] splitter, Message msg){

        if(splitter.length == 4){

            if(msg.getMentionedUsers().size() == 1){

                AdminInstance admin = new AdminInstance(msg.getMentionedUsers().get(0).getName(),
                        msg.getMentionedUsers().get(0).getId(),
                        System.currentTimeMillis());

                DeveloperCommandUtils.addAdmin(admin, success -> {

                    if(success){
                        msg.reply(DiscordUtils.createReplyEmbed("", "Úspěsně jsi přidal admina `" + admin.getNick() + "`", ReplyEmbedEnum.SUCCESS));
                    }
                    else {
                        msg.reply(DiscordUtils.createReplyEmbed("", "Vznikla chyba v SoftBotu při přidávání admina `" + admin.getNick() + "`", ReplyEmbedEnum.APP_ERROR));
                    }

                });

            }
            else {
                msg.reply(DiscordUtils.createReplyEmbed("Špatný formát", "Zadal jsi špatný formát příkazu. Musíš označit uživatele, kterého chceš přidat.\n\nPoužití příkazu\n`!sbp admin add <označení uživatele>`", ReplyEmbedEnum.ERROR));
            }

        }

    }

    private static void remove(String[] splitter, Message msg){

    }

}
