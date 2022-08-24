package ComponentButtons;

import Database.GameUtils;
import Database.TeamUtils;
import Enums.GameStatusEnum;
import Enums.ReplyEmbedEnum;
import Enums.TeamStatusEnum;
import Instances.CalendarGameInstance;
import Instances.GameInstance;
import Instances.TeamInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;

import java.util.Comparator;

public class TeamButton {

    public static void run(MessageComponentCreateEvent event){

        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String custom_id = messageComponentInteraction.getCustomId();

        if (custom_id.contains("ncodes-softbot-pending-team-approve-")){
            onApprove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if(custom_id.contains("ncodes-softbot-pending-team-deny")) {
            onDeny(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }
        else if (custom_id.contains("ncodes-softbot-pending-team-remove")){
            onRemove(messageComponentInteraction.getUser().getId(), custom_id, messageComponentInteraction.getMessage());
        }

    }

    private static void onApprove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-team-approve-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof TeamInstance){

            TeamInstance team = ((TeamInstance) Bot.getPendingData().getCheckingData().get(user_id));
            team.setStatus(TeamStatusEnum.APPROVED);

            Bot.getPendingData().getCheckingData().remove(user_id);
            Bot.getTeamUtil().getTeams().add(team);

            TeamUtils.updateTeamStatus(team.getId(), team.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("Povolení", "Tomuto týmu bylo úspěšně povoleno jeho vytvoření", "TeamButton.onApprove", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tomuto týmnu bylo neúspěšně povoleno jeho vytvoření. Zkuste to prosím později.", "TeamButton.onApprove", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

    private static void onDeny(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-team-deny-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof TeamInstance){

            TeamInstance team = ((TeamInstance) Bot.getPendingData().getCheckingData().get(user_id));
            team.setStatus(TeamStatusEnum.DENIED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            TeamUtils.updateTeamStatus(team.getId(), team.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("Zakázáno", "Tomuto týmu bylo úspěšně zakázáno jeho vytvoření", "TeamButton.onDeny", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tomuto týmnu bylo neúspěšně zakázáno jeho vytvoření. Zkuste to prosím později.", "TeamButton.onDeny", ReplyEmbedEnum.APP_ERROR));
                }

            });

        }

    }

    private static void onRemove(long user_id, String custom_id, Message message){

        String original_user_id = custom_id.replace("ncodes-softbot-pending-team-remove-", "");

        if(!String.valueOf(user_id).equals(original_user_id))
            return;

        if(Bot.getPendingData().getCheckingData().get(user_id) instanceof TeamInstance){

            TeamInstance team = ((TeamInstance) Bot.getPendingData().getCheckingData().get(user_id));
            team.setStatus(TeamStatusEnum.REMOVED);

            Bot.getPendingData().getCheckingData().remove(user_id);

            TeamUtils.updateTeamStatus(team.getId(), team.getStatus(), success -> {

                if(success){
                    message.reply(DiscordUtils.createReplyEmbed("Smazání", "Tento tým byl úspěšně smazán", "TeamButton.onRemove", ReplyEmbedEnum.SUCCESS));
                }
                else {
                    message.reply(DiscordUtils.createReplyEmbed("", "Tento tým byl neúšpěšně smazán. Zkuste to prosím později.", "TeamButton.onRemove", ReplyEmbedEnum.ERROR));
                }

            });

        }

    }

}
