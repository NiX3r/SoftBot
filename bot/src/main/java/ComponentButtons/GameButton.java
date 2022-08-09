package ComponentButtons;

import Database.GameUtils;
import Enums.GameStatusEnum;
import Enums.ReplyEmbedEnum;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;

import java.util.Comparator;

public class GameButton {

    public static void run(MessageComponentCreateEvent event){

        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String custom_id = messageComponentInteraction.getCustomId();

        if (custom_id.contains("ncodes-softbot-pending-game-approve-")){
            onApprove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if(custom_id.contains("ncodes-softbot-pending-game-deny")) {
            onDeny(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if (custom_id.contains("ncodes-softbot-pending-game-remove")){
            onRemove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }

    }

    private static void onApprove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-game-approve-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof GameInstance){

            GameInstance game = ((GameInstance) Bot.getPendingData().getCheckingData().get(user_id));
            game.setStatus(GameStatusEnum.APPROVED);

            Bot.getPendingData().getCheckingData().remove(user_id);
            Bot.getCalendar().getGames().add(game);
            Bot.getCalendar().addCalendarGame(game);
            Bot.getCalendar().getCalendar().sort(Comparator.comparingLong(CalendarGameInstance::getStart_date));

            GameUtils.updateGameStatus(game.getId(), game.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("", "Této hře bylo úspěšně povoleno její vytvoření", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Této hře bylo neúspěšně povoleno její vytvoření. Zkuste to prosím později.", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

    private static void onDeny(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-game-deny-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof GameInstance){

            GameInstance game = ((GameInstance) Bot.getPendingData().getCheckingData().get(user_id));
            game.setStatus(GameStatusEnum.DENIED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            GameUtils.updateGameStatus(game.getId(), game.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("", "Této hře bylo úspěšně zakázáno její vytvoření", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Této hře bylo neúspěšně zakázáno její vytvoření. Zkuste to prosím později.", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

    private static void onRemove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-game-remove-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof GameInstance){

            GameInstance game = ((GameInstance) Bot.getPendingData().getCheckingData().get(user_id));
            game.setStatus(GameStatusEnum.REMOVED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            GameUtils.updateGameStatus(game.getId(), game.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("", "Tato hra byla úspěšně smazána", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tuto hru se nepodařilo smazat. Zkuste to prosím později.", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

}
