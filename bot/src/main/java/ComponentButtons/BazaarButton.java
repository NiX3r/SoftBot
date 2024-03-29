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
import org.javacord.api.entity.user.User;
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

        if(Bot.getCheckingData().get(user_id) instanceof BazaarInstance){

            BazaarInstance bazaar = ((BazaarInstance) Bot.getCheckingData().get(user_id));
            User user = DiscordUtils.getUserById(bazaar.getCreator());
            bazaar.setStatus(BazaarStatusEnum.APPROVED);

            Bot.getCheckingData().remove(user_id);
            Bot.getBazaar().getBazaar().add(bazaar);

            BazaarUtils.updateBazaarStatus(bazaar.getId(), bazaar.getStatus(), success -> {

                if(success){
                    DiscordUtils.sendApprovedBazaarAnnouncementEmbed(bazaar, success_sent -> {
                        if(user != null)
                            user.sendMessage("Tvoje nabídka/poptávka s id `" + bazaar.getId() + "` a jménem `" + bazaar.getName() + "` byla povolena").join();
                        message.reply(DiscordUtils.createReplyEmbed("Úspěšné povolení", "Této nabídce/poptávce bylo úspěšně povoleno jeho vytvoření a bylo zasláno oznámení na všechny servery", "BazaarButton.onApprove", ReplyEmbedEnum.SUCCESS));
                    });
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Této nabídce/poptávce bylo neúspěšně povoleno jeho vytvoření. Zkuste to prosím později.", "BazaarButton.onApprove", ReplyEmbedEnum.APP_ERROR));
                }

            });

        }

    }

    private static void onDeny(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-bazaar-deny-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getCheckingData().get(user_id) instanceof BazaarInstance){

            BazaarInstance bazaar = ((BazaarInstance) Bot.getCheckingData().get(user_id));
            User user = DiscordUtils.getUserById(bazaar.getCreator());
            bazaar.setStatus(BazaarStatusEnum.DENIED);

            Bot.getCheckingData().remove(user_id);

            BazaarUtils.updateBazaarStatus(bazaar.getId(), bazaar.getStatus(), success -> {

                if(success){
                    if(user != null)
                        user.sendMessage("Tvoje nabídka/poptávka s id `" + bazaar.getId() + "` a jménem `" + bazaar.getName() + "` byla zakázána").join();
                    message.reply(DiscordUtils.createReplyEmbed("Úspěšné zakázání", "Této nabídce/poptávce bylo úspěšně zakázáno jeho vytvoření", "BazaarButton.onDeny", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Této nabídce/poptávce bylo neúspěšně zakázáno jeho vytvoření. Zkuste to prosím později.", "BazaarButton.onDeny", ReplyEmbedEnum.APP_ERROR));
                }

            });

        }

    }

    private static void onRemove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-bazaar-remove-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getCheckingData().get(user_id) instanceof BazaarInstance){

            BazaarInstance bazaar = ((BazaarInstance) Bot.getCheckingData().get(user_id));
            User user = DiscordUtils.getUserById(bazaar.getCreator());
            bazaar.setStatus(BazaarStatusEnum.REMOVED);

            Bot.getCheckingData().remove(user_id);

            BazaarUtils.updateBazaarStatus(bazaar.getId(), bazaar.getStatus(), success -> {

                if(success){
                    if(user != null)
                        user.sendMessage("Tvoje nabídka/poptávka s id `" + bazaar.getId() + "` a jménem `" + bazaar.getName() + "` byla smazána").join();
                    message.reply(DiscordUtils.createReplyEmbed("Úspěšné smazání", "Tato poptávka/nabídka byla úspěšně smazán", "BazaarButton.onRemove", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tato poptávka/nabídka byla neúšpěšně smazán. Zkuste to prosím později.", "BazaarButton.onRemove", ReplyEmbedEnum.APP_ERROR));
                }

            });

        }

    }

}
