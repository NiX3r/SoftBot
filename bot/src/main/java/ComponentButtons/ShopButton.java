package ComponentButtons;

import Database.GameUtils;
import Database.ShopUtils;
import Enums.GameStatusEnum;
import Enums.ReplyEmbedEnum;
import Enums.ShopStatusEnum;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Instances.ShopInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;

import java.util.Comparator;

public class ShopButton {

    public static void run(MessageComponentCreateEvent event){

        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String custom_id = messageComponentInteraction.getCustomId();

        if (custom_id.contains("ncodes-softbot-pending-shop-approve-")){
            onApprove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if(custom_id.contains("ncodes-softbot-pending-shop-deny")) {
            onDeny(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if (custom_id.contains("ncodes-softbot-pending-shop-remove")){
            onRemove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }

    }

    private static void onApprove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-shop-approve-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof ShopInstance){

            ShopInstance game = ((ShopInstance) Bot.getPendingData().getCheckingData().get(user_id));
            game.setStatus(ShopStatusEnum.APPROVED);

            Bot.getPendingData().getCheckingData().remove(user_id);
            Bot.getShop().getShops().add(game);

            ShopUtils.updateShopStatus(game.getId(), game.getStatus(), success -> {
                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("Povolení", "Tomuto obchodu bylo úspěšně povoleno její vytvoření a bylo rozesláno oznámení na všechny servery", "GameButton.onApprove", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tomuto obchodu bylo neúspěšně povoleno její vytvoření. Zkuste to prosím později.", "GameButton.onApprove", ReplyEmbedEnum.APP_ERROR));
                }
            });

        }

    }

    private static void onDeny(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-shop-deny-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof ShopInstance){

            ShopInstance game = ((ShopInstance) Bot.getPendingData().getCheckingData().get(user_id));
            game.setStatus(ShopStatusEnum.DENIED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            ShopUtils.updateShopStatus(game.getId(), game.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("Zakázání", "Tomuto obchodu bylo úspěšně zakázáno jeho vytvoření.", "GameButton.onDeny", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tomuto obchodu bylo neúspěšně zakázáno jeho vytvoření. Zkuste to prosím později.", "GameButton.onDeny", ReplyEmbedEnum.APP_ERROR));
                }

            });

        }

    }

    private static void onRemove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-shop-remove-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof ShopInstance){

            ShopInstance game = ((ShopInstance) Bot.getPendingData().getCheckingData().get(user_id));
            game.setStatus(ShopStatusEnum.REMOVED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            ShopUtils.updateShopStatus(game.getId(), game.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("Smazáni", "Tento obchod byl úspěšně smazána.", "GameButton.onRemove", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tento obchod se nepodařilo smazat. Zkuste to prosím později.", "GameButton.onRemove", ReplyEmbedEnum.APP_ERROR));
                }

            });

        }

    }

}
