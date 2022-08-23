package SlashCommands;

import Database.DatabaseUtils;
import Database.DeveloperUtils;
import Enums.LogTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.AdminInstance;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class AdminCommand {

    public static void run(SlashCommandInteraction interaction){

        if(!interaction.getUser().getIdAsString().equals("397714589548019722")){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro vývojáře SoftBota", "ProgrammerCommand.run", ReplyEmbedEnum.WARNING)).respond().join();
            return;
        }

        Utils.LogSystem.log(LogTypeEnum.INFO, "programmer comand catched by '" + interaction.getUser().getName() + "' on server '" + (interaction.getServer().isPresent() ? interaction.getServer().get().getName() : "PrivateMessage") + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        switch (interaction.getOptions().get(0).getName()){

            case "add":
                add(interaction);
                break;
            case "remove":
                remove(interaction);
                break;

        }

    }

    private static void add(SlashCommandInteraction interaction){

        String target_id = interaction.getArguments().get(0).getStringValue().get();
        User target = null;

        for(Server server : Bot.getBot().getServers()){
            if(server.getMemberById(target_id).isPresent()){
                target = server.getMemberById(target_id).get();
                break;
            }
        }

        if(target == null){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující ID", "Uživatel s tímto ID není na žádném serveru", "AdminCommand.add", ReplyEmbedEnum.WARNING)).respond().join();
            return;
        }

        AdminInstance admin = new AdminInstance(target.getName(),
                target.getId(),
                System.currentTimeMillis());
        Bot.getAdmins().add(admin);

        DeveloperUtils.addAdmin(admin, success -> {

            if(success){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Přidání", "Úspěsně jsi přidal admina `" + admin.getNick() + "`", "AdminCommand.add", ReplyEmbedEnum.SUCCESS)).respond().join();
            }
            else {
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("", "Vznikla chyba v SoftBotu při přidávání admina `" + admin.getNick() + "`", "ProgrammerCommand.add", ReplyEmbedEnum.APP_ERROR)).respond().join();
            }

        });

    }

    private static void remove(SlashCommandInteraction interaction){

        long selectedAdmin = DatabaseUtils.decodeDiscordId(interaction.getArguments().get(0).getStringValue().get());

        if(!Bot.removeAdminByDiscordId(selectedAdmin)){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Neexistující ID", "Zadané ID neexistuje. Prosím zadej skutečné ID", "ProgrammerCommand.remove", ReplyEmbedEnum.ERROR)).respond().join();
            return;
        }

        DeveloperUtils.removeAdmin(selectedAdmin, success -> {

            if(success){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Smazání", "Úspěsně jsi smazal admina `" + selectedAdmin + "`", "ProgrammerCommand.remove", ReplyEmbedEnum.SUCCESS)).respond().join();
            }
            else {
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("", "Vznikla chyba v SoftBotu při odebírání admina `" + selectedAdmin + "`", "ProgrammerCommand.remove", ReplyEmbedEnum.APP_ERROR)).respond().join();
            }

        });

    }

}
