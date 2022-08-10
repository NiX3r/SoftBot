package ComponentButtons;

import Database.BazaarUtils;
import Database.TeamUtils;
import Enums.BazaarStatusEnum;
import Enums.ReplyEmbedEnum;
import Enums.TeamStatusEnum;
import Instances.BazaarInstance;
import Instances.TeamInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;

public class BazaarButton {

    public static void run(MessageComponentCreateEvent event){

        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String custom_id = messageComponentInteraction.getCustomId();

        if (custom_id.contains("ncodes-softbot-pending-bazaar-approve-")){
            onApprove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if(custom_id.contains("ncodes-softbot-pending-bazaar-deny")) {
            onDeny(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if (custom_id.contains("ncodes-softbot-pending-bazaar-remove")){
            onRemove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }

    }

    private static void onApprove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-bazaar-approve-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof BazaarInstance){

            BazaarInstance bazaar = ((BazaarInstance) Bot.getPendingData().getCheckingData().get(user_id));
            bazaar.setStatus(BazaarStatusEnum.APPROVED);

            Bot.getPendingData().getCheckingData().remove(user_id);
            Bot.getBazaar().getBazaar().add(bazaar);

            BazaarUtils.updateBazaarStatus(bazaar.getId(), bazaar.getStatus(), bazaar.getCreator_ping(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("", "Této nabídce/poptávce bylo úspěšně povoleno jeho vytvoření", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Této nabídce/poptávce bylo neúspěšně povoleno jeho vytvoření. Zkuste to prosím později.", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

    private static void onDeny(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-bazaar-deny-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof BazaarInstance){

            BazaarInstance bazaar = ((BazaarInstance) Bot.getPendingData().getCheckingData().get(user_id));
            bazaar.setStatus(BazaarStatusEnum.DENIED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            BazaarUtils.updateBazaarStatus(bazaar.getId(), bazaar.getStatus(), bazaar.getCreator_ping(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("", "Této nabídce/poptávce bylo úspěšně zakázáno jeho vytvoření", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Této nabídce/poptávce bylo neúspěšně zakázáno jeho vytvoření. Zkuste to prosím později.", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

    private static void onRemove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-bazaar-remove-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof BazaarInstance){

            BazaarInstance bazaar = ((BazaarInstance) Bot.getPendingData().getCheckingData().get(user_id));
            bazaar.setStatus(BazaarStatusEnum.REMOVED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            BazaarUtils.updateBazaarStatus(bazaar.getId(), bazaar.getStatus(), bazaar.getCreator_ping(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("", "Tato poptávka/nabídka byla úspěšně smazán", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tato poptávka/nabídka byla neúšpěšně smazán. Zkuste to prosím později.", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

}
