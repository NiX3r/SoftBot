package Listeners;

import ComponentButtons.BazaarButton;
import ComponentButtons.GameButton;
import ComponentButtons.TeamButton;
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
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.util.Comparator;
import java.util.HashMap;

public class nMessageComponentCreateListener implements MessageComponentCreateListener {

    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String custom_id = messageComponentInteraction.getCustomId();

        if (custom_id.contains("ncodes-softbot-pending-game-")){
            GameButton.run(event);
            event.getMessageComponentInteraction().acknowledge();
        }
        else if (custom_id.contains("ncodes-softbot-pending-team-")){
            TeamButton.run(event);
            event.getMessageComponentInteraction().acknowledge();
        }
        else if (custom_id.contains("ncodes-softbot-pending-bazaar-")){
            BazaarButton.run(event);
            event.getMessageComponentInteraction().acknowledge();
        }

    }

}
