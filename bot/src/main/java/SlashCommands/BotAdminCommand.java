package SlashCommands;

import Database.*;
import Enums.BazaarStatusEnum;
import Enums.ReplyEmbedEnum;
import Instances.*;
import Utils.Bot;
import Utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;

public class BotAdminCommand {

    public static void announcement(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            String content = interaction.getArguments().get(0).getStringValue().get().replaceAll("<n>", "\n");
            DiscordUtils.sendAnnouncementEmbed(content, interaction.getUser().getName(), interaction.getUser().getAvatar().getUrl().toString(), success -> {
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Zasláno", "Upozornění bylo úspěšně zasláno na všechny servery.", "BotAdminCommand.announcement", ReplyEmbedEnum.SUCCESS)).respond().join();
            });
        }
    }

    public static void ban(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "BotAdminCommand.run", ReplyEmbedEnum.WARNING));
        }
    }

    public static void unban(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Nehotová funkce", "Tato funkce ještě nebyla spuštěna. Prosím zkuste ji později.", "BotAdminCommand.run", ReplyEmbedEnum.WARNING));
        }
    }

    public static void server(SlashCommandInteraction interaction){
        if(checkPermission(interaction)){
            ServerCommand.run(interaction);
        }
    }

    public static void pending(SlashCommandInteraction interaction) {
        if(checkPermission(interaction)){
            switch (interaction.getOptions().get(0).getName()){
                case "game":
                    game(interaction);
                    break;
                case "team":
                    team(interaction);
                    break;
                case "bazaar":
                    bazaar(interaction);
                    break;
                case "shop":
                    shop(interaction);
                    break;
            }
        }
    }

    private static void game(SlashCommandInteraction interaction){

        GameUtils.getLastestPendingGame(game -> {

            if(game == null){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Žádná data", "Již byla zpracována všechna data, která být zpracována měla. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.game", ReplyEmbedEnum.WARNING)).respond().join();
                return;
            }

            for(Object o : Bot.getCheckingData().values()){
                if(o instanceof GameInstance){
                    if(((GameInstance)o).getId() == game.getId()){
                        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Hra již v kontrole", "Již se zpracovává poslední hra. Zkus zpracovat jiná data nebo počkej, než bude hra zpracována. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.team", ReplyEmbedEnum.WARNING)).respond().join();
                        return;
                    }
                }
            }

            String repeat_string = "žádné";

            switch (game.getRepeat_date()){
                case "W":
                    repeat_string = "týdenní";
                    break;
                case "M":
                    repeat_string = "měsíční";
                    break;
                case "Y":
                    repeat_string = "roční";
                    break;
            }

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(Color.decode("#D1A841"))
                    .setTitle("Potvrzení registrace hry")
                    .setImage(game.getThumbnail() == null ? "" : game.getThumbnail())
                    .addField("ID", game.getId() + "")
                    .addInlineField("Jméno", game.getName())
                    .addInlineField("Začátek akce", DatabaseUtils.encodeDateTime(game.getStart_date()))
                    .addInlineField("Konec akce", DatabaseUtils.encodeDateTime(game.getEnd_date()))
                    .addInlineField("Lokace", game.getLocation())
                    .addInlineField("Vstupné", String.valueOf(game.getPrice()))
                    .addInlineField("Opakování", repeat_string)
                    .addInlineField("Typ", game.getType().equals("PB") ? "Plácko bitka" : game.getType())
                    .addInlineField("IP adresa", game.getIp_address())
                    .setDescription(game.getDescription())
                    .setFooter("Verze: " + Bot.getVersion());

            MessageBuilder msg_builder = new MessageBuilder()
                    .setEmbed(builder)
                    .addComponents(
                            ActionRow.of(Button.success("ncodes-softbot-pending-game-approve-" + interaction.getUser().getId(), "Povolit"),
                                    Button.danger("ncodes-softbot-pending-game-deny-" + interaction.getUser().getId(), "Zakákat"),
                                    Button.danger("ncodes-softbot-pending-game-remove-" + interaction.getUser().getId(), "Smazat"))
                    );

            msg_builder.send(interaction.getChannel().get()).thenAccept(success -> {

                Bot.getCheckingData().put(interaction.getUser().getId(), game);

            });
            interaction.createImmediateResponder().setContent("hotovo").respond().join();

        });
    }

    private static void team(SlashCommandInteraction interaction){

        TeamUtils.getLastestPendingTeam(team -> {

            if(team == null){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Žádná data", "Již byla zpracována všechna data, která být zpracována měla. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.team", ReplyEmbedEnum.WARNING)).respond().join();
                return;
            }

            for(Object o : Bot.getCheckingData().values()){
                if(o instanceof TeamInstance){
                    if(((TeamInstance)o).getId() == team.getId()){
                        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Tým již v kontrole", "Již se zpracovává poslední tým. Zkus zpracovat jiná data nebo počkej, než bude tým zpracován. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.team", ReplyEmbedEnum.WARNING)).respond().join();
                        return;
                    }
                }
            }

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(Color.decode("#D1A841"))
                    .setTitle("Potvrzení registrace týmu")
                    .setImage(team.getThumbnail() == null ? "" : team.getThumbnail())
                    .addInlineField("ID", team.getId() + "")
                    .addInlineField("Jméno", team.getName())
                    .addInlineField("IP adresa", team.getIp_address())
                    .addInlineField("Web", team.getWebsite())
                    .addInlineField("Typ", team.getType().replace("CQB&MS", "CQB a MilSim"))
                    .addInlineField("Discord Server ID", team.getDiscord_server_id() + "")
                    .setDescription(team.getDescription())
                    .setFooter("Verze: " + Bot.getVersion());

            MessageBuilder msg_builder = new MessageBuilder()
                    .setEmbed(builder)
                    .addComponents(
                            ActionRow.of(Button.success("ncodes-softbot-pending-team-approve-" + interaction.getUser().getId(), "Povolit"),
                                    Button.danger("ncodes-softbot-pending-team-deny-" + interaction.getUser().getId(), "Zakákat"),
                                    Button.danger("ncodes-softbot-pending-team-remove-" + interaction.getUser().getId(), "Smazat"))
                    );

            msg_builder.send(interaction.getChannel().get()).thenAccept(success -> {

                Bot.getCheckingData().put(interaction.getUser().getId(), team);

            });
            interaction.createImmediateResponder().setContent("hotovo").respond().join();

        });
    }

    private static void bazaar(SlashCommandInteraction interaction){

        BazaarUtils.getLastestPendingBazaar(bazaar -> {

            if(bazaar == null){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Žádná data", "Již byla zpracována všechna data, která být zpracována měla. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.bazaar", ReplyEmbedEnum.WARNING)).respond().join();
                return;
            }
            for(Object o : Bot.getCheckingData().values()){
                if(o instanceof BazaarInstance){
                    if(((BazaarInstance)o).getId() == bazaar.getId()){
                        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Bazar již v kontrole", "Již se zpracovává poslední bazar. Zkus zpracovat jiná data nebo počkej, než bude bazar zpracován. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.team", ReplyEmbedEnum.WARNING)).respond().join();
                        return;
                    }
                }
            }
            String user_ping = DiscordUtils.getNickPingById(bazaar.getUser_id());

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(Color.decode("#D1A841"))
                    .setTitle("Potvrzení registrace bazaru")
                    .addInlineField("ID", bazaar.getId() + "")
                    .addInlineField("Jméno", bazaar.getName())
                    .addInlineField("IP adresa", bazaar.getIp_address())
                    .addInlineField("Majitel nabídky/poptávky id", bazaar.getUser_id() + "")
                    .addInlineField("Majitel nabídky/poptávky nick", user_ping == null ? "nenastaveno" : user_ping)
                    .addInlineField("Typ", bazaar.getType().toString())
                    .addInlineField("PSČ", bazaar.getZip() + "")
                    .addInlineField("Cena", bazaar.getPrice() + "")
                    .setDescription(bazaar.getDescription())
                    .setFooter("Verze: " + Bot.getVersion());

            MessageBuilder msg_builder = new MessageBuilder()
                    .setEmbed(builder);

            msg_builder.addComponents(
                    ActionRow.of(org.javacord.api.entity.message.component.Button.success("ncodes-softbot-pending-bazaar-approve-" + interaction.getUser().getId(), "Povolit"),
                            org.javacord.api.entity.message.component.Button.danger("ncodes-softbot-pending-bazaar-deny-" + interaction.getUser().getId(), "Zakákat"),
                            Button.danger("ncodes-softbot-pending-bazaar-remove-" + interaction.getUser().getId(), "Smazat"))
            );
            msg_builder.send(interaction.getChannel().get()).thenAccept(success -> {

                Bot.getCheckingData().put(interaction.getUser().getId(), bazaar);

            });
            interaction.createImmediateResponder().setContent("hotovo").respond().join();

        });

    }

    private static void shop(SlashCommandInteraction interaction){

        ShopUtils.getLatestPendingShop(shop -> {

            if(shop == null){
                interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Žádná data", "Již byla zpracována všechna data, která být zpracována měla. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.shop", ReplyEmbedEnum.WARNING)).respond().join();
                return;
            }
            for(Object o : Bot.getCheckingData().values()){
                if(o instanceof ShopInstance){
                    if(((ShopInstance)o).getId() == shop.getId()){
                        interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Obchod již v kontrole", "Již se zpracovává poslední obchod. Zkus zpracovat jiná data nebo počkej, než bude obchod zpracován. Děkujeme za čas, který jsi chtěl věnovat SoftBotovi.", "PendingCommand.team", ReplyEmbedEnum.WARNING)).respond().join();
                        return;
                    }
                }
            }
            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(Color.decode("#D1A841"))
                    .setTitle("Potvrzení registrace obchodu")
                    .setImage(shop.getThumbnail())
                    .addField("ID", shop.getId() + "")
                    .addInlineField("Název", shop.getName())
                    .addInlineField("Slevový kód", shop.getVoucher().equals("") ? "nenastaven" : shop.getVoucher())
                    .addInlineField("Web", shop.getWebsite())
                    .addInlineField("Adresa", shop.getLocation().equals("") ? "nenastavena" : shop.getLocation())
                    .addInlineField("ZIP", String.valueOf(shop.getZip()))
                    .addInlineField("IP adresa", shop.getIp_address())
                    .setDescription(shop.getDescription())
                    .setFooter("Verze: " + Bot.getVersion());

            MessageBuilder msg_builder = new MessageBuilder()
                    .setEmbed(builder)
                    .addComponents(
                            ActionRow.of(Button.success("ncodes-softbot-pending-shop-approve-" + interaction.getUser().getId(), "Povolit"),
                                    Button.danger("ncodes-softbot-pending-shop-deny-" + interaction.getUser().getId(), "Zakákat"),
                                    Button.danger("ncodes-softbot-pending-shop-remove-" + interaction.getUser().getId(), "Smazat"))
                    );

            msg_builder.send(interaction.getChannel().get()).thenAccept(success -> {

                Bot.getCheckingData().put(interaction.getUser().getId(), shop);

            });
            interaction.createImmediateResponder().setContent("hotovo").respond().join();

        });

    }

    private static boolean checkPermission(SlashCommandInteraction interaction){
        if(!Bot.isUserAdmin(interaction.getUser().getId())){
            interaction.createImmediateResponder().addEmbed(DiscordUtils.createReplyEmbed("Práva", "Tyto příkazy jsou pouze pro Admin tým SoftBota", "AdminCommand.run", ReplyEmbedEnum.WARNING)).respond().join();
            return false;
        }
        return true;
    }
}
