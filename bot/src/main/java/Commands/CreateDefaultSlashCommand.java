package Commands;

import Utils.Bot;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;

public class CreateDefaultSlashCommand {

    public static void create(){

        SlashCommand command = SlashCommand.with("sb", "Main command of SoftBot",
                Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "help", "Subcommand for help menu"),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, "game", "Subcommand group for game section", Arrays.asList(
                                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "create", "Subcommand for show game create form"),
                                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "list", "Subcommand for show list of games indexed by pages 10 games per page", Arrays.asList(
                                        SlashCommandOption.create(SlashCommandOptionType.LONG, "index", "Index of a page to show", false)
                                )),
                                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "to", "Subcommand for show games to specific date", Arrays.asList(
                                        SlashCommandOption.create(SlashCommandOptionType.STRING, "date", "Date until which games are shown", true)
                                )),
                                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "show", "Subcommand for show specific game by it's ID", Arrays.asList(
                                        SlashCommandOption.create(SlashCommandOptionType.LONG, "id", "ID of a specific game", true)
                                ))
                        )),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, "team", "Subcommand group for team section", Arrays.asList(
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
                        )),
                        SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "reddit", "Subcommand for show a random post from r/Airsoft"),
                        SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "invite", "Subcommand for show invite link to invite SoftBot on your server"),
                        SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "credits", "Subcommand for show every member who helped in developing SoftBot"),
                        SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "sponsors", "Subcommand for show every SoftBot's sponsors"),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, "offer", "Subcommand group for offer section", Arrays.asList(
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
                        )),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, "inquiry", "Subcommand group for inquiry section", Arrays.asList(
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
                        )),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "channel", "Subcommand for set announcements channel", Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.MENTIONABLE, "channel-ping", "Channel ping of channel which should be set", true)
                        )),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "team-role", "Subcommand for set team role", Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.MENTIONABLE, "role-ping", "Role ping of role which should be set", true)
                        ))

                )).createGlobal(Bot.getBot()).join();

    }

}
