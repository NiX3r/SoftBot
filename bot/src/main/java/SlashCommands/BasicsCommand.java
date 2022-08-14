package SlashCommands;

import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.List;

public class BasicsCommand {

    public static void run(SlashCommandInteraction interaction){

        interaction.createImmediateResponder().setContent("Just testing").respond();

    }

}
