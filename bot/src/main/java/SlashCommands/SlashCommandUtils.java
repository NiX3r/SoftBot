package SlashCommands;

import Enums.LogTypeEnum;
import Utils.Bot;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class SlashCommandUtils {

    public static void create(){

        Utils.LogSystem.log(LogTypeEnum.INFO, "creating global commands", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        SlashCommand.with("help", "command for help menu").createGlobal(Bot.getBot()).join();
        SlashCommand.with("reddit", "command for show a random post from r/Airsoft").createGlobal(Bot.getBot()).join();
        SlashCommand.with("invite", "command for show invite link to invite SoftBot on your server").createGlobal(Bot.getBot()).join();
        SlashCommand.with("credits", "command for show every member who helped in developing SoftBot").createGlobal(Bot.getBot()).join();
        SlashCommand.with("sponsors", "command for show every SoftBot's sponsors").createGlobal(Bot.getBot()).join();
        SlashCommand.with("channel", "command for set up announcement channel", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.CHANNEL, "channel", "channel ping for a announcement channel", true)
        )).setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(Bot.getBot()).join();
        SlashCommand.with("team-role", "command for set up team role", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.ROLE, "role", "role ping for a team role", true)
        )).setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(Bot.getBot()).join();

        Utils.LogSystem.log(LogTypeEnum.INFO, "other commands created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        SlashCommand.with("game", "group for game section", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "create", "Subcommand for show game create form"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "list", "Subcommand for show list of games indexed by pages 10 games per page", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "index", "Index of a page to show", false)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "to", "Subcommand for show games to specific date", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "date", "Date since games are displayed till today", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "show", "Subcommand for show specific game by it's ID", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific game", true)
                ))
        )).createGlobal(Bot.getBot()).join();

        Utils.LogSystem.log(LogTypeEnum.INFO, "game commands created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        SlashCommand.with("team", "group for team section", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "create", "Subcommand for show team create form"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "list", "Subcommand for show list of teams indexed by pages 10 teams per page", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "index", "Index of a page to show", false)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "invite", "Subcommand for show invite link to team's Discord server", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific team", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "show", "Subcommand for show specific team by it's ID", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific team", true)
                ))
        )).createGlobal(Bot.getBot()).join();

        Utils.LogSystem.log(LogTypeEnum.INFO, "team commands created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        SlashCommand.with("offer", "group for offer section", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "create", "Subcommand for show offer create form"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "list", "Subcommand for show list of offers indexed by pages 10 offer per page", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "index", "Index of a page to show", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "file", "Subcommand for set files for a specific offer", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific offer", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "password", "Password for edit offer", true),
                        SlashCommandOption.create(SlashCommandOptionType.ATTACHMENT, "attachment", "Attachment that will replace old attachment", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "show", "Subcommand for show specific offer by it's ID", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific offer", true)
                ))
        )).createGlobal(Bot.getBot()).join();

        Utils.LogSystem.log(LogTypeEnum.INFO, "offer commands created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());


        SlashCommand.with("inquiry", "group for inquiry section", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "create", "Subcommand for show inquiry create form"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "list", "Subcommand for show list of inquires indexed by pages 10 inquiry per page", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "index", "Index of a page to show", false)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "file", "Subcommand for set files for a specific inquiry", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific inquiry", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "password", "Password for edit inquiry", true),
                        SlashCommandOption.create(SlashCommandOptionType.ATTACHMENT, "attachment", "Attachment that will replace old attachment", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "show", "Subcommand for show specific inquiry by it's ID", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific inquiry", true)
                ))
        )).createGlobal(Bot.getBot()).join();

        Utils.LogSystem.log(LogTypeEnum.INFO, "inquiry commands created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        SlashCommand.with("ban", "command for ban user", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "user-id", "ID of a target user", true),
                SlashCommandOption.createWithChoices(SlashCommandOptionType.DECIMAL, "type", "Type of ban", true, Arrays.asList(
                        SlashCommandOptionChoice.create("bazaar", 1),
                        SlashCommandOptionChoice.create("anywhere", 2)
                )),
                SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "Reason of a ban", true)
        )).createGlobal(Bot.getBot()).join();

        SlashCommand.with("unban", "command for unban user", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "user-id", "ID of a target user", true),
                SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "Reason of a ban", true)
        )).createGlobal(Bot.getBot()).join();

        SlashCommand.with("server", "command group for a server section", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "list", "Subcommand for a server list"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "link", "Subcommand for create a link to a server", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "server-id", "ID of a target server", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "disconnect", "Subcommand for disconnect from a server", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "server-id", "ID of a target server", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "Reason of a disconnect", true)
                        )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "ban", "Subcommand for ban server from using bot", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "server-id", "ID of a target server", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "Reason of a disconnect", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "unban", "Subcommand for an unban server from using bot", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "server-id", "ID of a target server", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "Reason of a disconnect", true)
                ))
        )).createGlobal(Bot.getBot()).join();

        SlashCommand.with("announcement", "command for an announcement system", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "content", "Content of an announcement", true)
        )).createGlobal(Bot.getBot()).join();

        SlashCommand.with("pending", "command for administrate pending data", Arrays.asList(
                SlashCommandOption.createWithChoices(SlashCommandOptionType.DECIMAL, "type", "Type of pending data", true, Arrays.asList(
                        SlashCommandOptionChoice.create("game", 1),
                        SlashCommandOptionChoice.create("team", 2),
                        SlashCommandOptionChoice.create("bazaar", 3),
                        SlashCommandOptionChoice.create("shop", 4)
                ))
        )).createGlobal(Bot.getBot()).join();

        Utils.LogSystem.log(LogTypeEnum.INFO, "bot admin commands created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        SlashCommand.with("admin", "command group for admin section", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "add", "Subcommand for add bot admin", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "user-id", "ID of a target user", true)
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "remove", "Subcommand for remove bot admin", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "user-id", "ID of a target user", true)
                ))
        )).createGlobal(Bot.getBot()).join();

        Utils.LogSystem.log(LogTypeEnum.INFO, "programmer commands created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }

    public static void delete(){

        try {
            Utils.LogSystem.log(LogTypeEnum.INFO, "trying to remove all commands", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            for(SlashCommand command : Bot.getBot().getGlobalSlashCommands().get()){

                if(command.getName().equals("help") || command.getName().equals("game") || command.getName().equals("team") ||
                        command.getName().equals("reddit") || command.getName().equals("invite") || command.getName().equals("credits") ||
                        command.getName().equals("sponsors") || command.getName().equals("offer") || command.getName().equals("inquiry") ||
                        command.getName().equals("channel") || command.getName().equals("team-role") || command.getName().equals("ban") ||
                        command.getName().equals("unban") || command.getName().equals("server") || command.getName().equals("announcement") ||
                        command.getName().equals("pending") || command.getName().equals("admin") ) {
                    Utils.LogSystem.log(LogTypeEnum.INFO, "deleting slash command " + command.getName(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                    command.deleteGlobal().join();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
