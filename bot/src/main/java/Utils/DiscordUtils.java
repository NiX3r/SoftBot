package Utils;

import Database.DatabaseUtils;
import Enums.BazaarTypeEnum;
import Enums.ReplyEmbedEnum;
import Instances.BazaarInstance;
import Instances.GameInstance;
import Instances.ServerOptionInstance;
import Instances.TeamInstance;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.Calendar;
import java.util.function.Consumer;

public class DiscordUtils {

    public static EmbedBuilder createReplyEmbed(String replyTopic, String replyMessage, String source, ReplyEmbedEnum replyEmbedEnum){

        Color c = null;
        String replyEnum = "";

        switch (replyEmbedEnum){

            case SUCCESS:
                c = Color.decode("#37d31f");
                replyEnum = "Úspěšnost";
                break;

            case WARNING:
                c = Color.decode("#d37f1f");
                replyEnum = "Upozornění";
                break;

            case ERROR:
                c = Color.decode("#d31f1f");
                replyEnum = "Chyba";
                break;

            case APP_ERROR:
                c = Color.decode("#1f28d3");
                replyEnum = "Chyba na straně aplikace";
                break;

            case EASTER_EGG:
                c = Color.decode("#7900ff");
                replyEnum = "Easter Egg";
                break;

        }

        return new EmbedBuilder()
                .setTitle(replyEnum + (replyTopic == null ? "" : " - " + replyTopic))
                .setColor(c)
                .setDescription(replyMessage)
                .setFooter("Zdroj: " + source + " | Verze: " + Bot.getVersion());

    }

    public static void sendAnnouncementEmbed(String content, String author, String author_avatar, Consumer<Boolean> callback){

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Oznamovací systém :ear:")
                .setDescription(content)
                .setColor(Color.decode("#D1A841"))
                .setFooter("Administrátor: " + author + " | Verze: " + Bot.getVersion(), author_avatar);

        sendEmbedOnEveryServerSystem(builder, content, success -> {
            callback.accept(true);
        });

    }

    public static void sendApprovedBazaarAnnouncementEmbed(BazaarInstance bazaar, Consumer<Boolean> callback){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle((bazaar.getType() == BazaarTypeEnum.INQUIRY ? "Poptávám: " : "Nabízím: ") + bazaar.getName())
                .addInlineField("Cena", String.valueOf(bazaar.getPrice()))
                .addInlineField("PSČ", String.valueOf(bazaar.getZip()))
                .addInlineField("Kontakt", getNickPingById(bazaar.getUser_id()))
                .setDescription(bazaar.getDescription())
                .setFooter("Verze: " + Bot.getVersion());

        sendEmbedOnEveryServerBazaar(builder, bazaar.getDescription(), success -> {
            callback.accept(true);
        });

    }

    public static void sendApprovedGameAnnouncementEmbed(GameInstance game, Consumer<Boolean> callback){
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
                .setTitle("Byla vytvořena nová hra: " + game.getName())
                .setImage(game.getThumbnail() == null ? "" : game.getThumbnail())
                .addField("ID", game.getId() + "")
                .addInlineField("Začátek akce", DatabaseUtils.encodeDateTime(game.getStart_date()))
                .addInlineField("Konec akce", DatabaseUtils.encodeDateTime(game.getEnd_date()))
                .addInlineField("Lokace", game.getLocation())
                .addInlineField("Vstupné", String.valueOf(game.getPrice()))
                .addInlineField("Opakování", repeat_string)
                .addInlineField("Typ", game.getType().equals("PB") ? "Plácko bitka" : game.getType())
                .setDescription(game.getDescription())
                .setFooter("Verze: " + Bot.getVersion());

        sendEmbedOnEveryServerGame(builder, game.getDescription(), success -> {
            callback.accept(true);
        });

    }

    public static void sendWeekPlanEmbed(String[] games_on_days, int year, int month, int start_day, Consumer<Boolean> callback){

        int to_day = start_day + 7;
        final int max_days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        if(to_day > max_days){
            to_day -= max_days;
            month++;
            if(month == 13)
                month = 1;
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#D1A841"))
                .setTitle("Týdenní plán her od " + start_day + "." + month + "." + year + " do " + to_day + "." + month + "." + year)
                .addField("Pondělí", games_on_days[0])
                .addField("Úterý", games_on_days[1])
                .addField("Středa", games_on_days[2])
                .addField("Čtvrtek", games_on_days[3])
                .addField("Pátek", games_on_days[4])
                .addField("Sobota", games_on_days[5])
                .addField("Neděle", games_on_days[6])
                .setFooter("Verze: " + Bot.getVersion());

        sendEmbedOnEveryServerGame(builder, "", success -> {
            callback.accept(true);
        });
    }

    public static String getNickPingById(long id){
        for(Server server : Bot.getBot().getServers()){
            if(server.getMemberById(id).isPresent()){
                return server.getMemberById(id).get().getMentionTag();
            }
        }
        return null;
    }

    public static User getUserById(long id){
        for(Server server : Bot.getBot().getServers()){
            if(server.getMemberById(id).isPresent()){
                return server.getMemberById(id).get();
            }
        }
        return null;
    }

    private static void sendEmbedOnEveryServerSystem(EmbedBuilder builder, String description, Consumer<Boolean> callback){

        Bot.getBot().getServers().forEach(server -> {

            ServerOptionInstance option = Bot.getServerOption(server.getId());

            if(option == null){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel system <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            if(option.getAnnouncement_channel_id() == -1){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel system <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            else {
                long announcement_channel_id = option.getAnnouncement_channel_id();

                server.getTextChannelById(announcement_channel_id).ifPresent(channel -> {
                    builder.setDescription(description);
                    channel.sendMessage(builder);
                });
            }

        });

        callback.accept(true);

    }

    private static void sendEmbedOnEveryServerGame(EmbedBuilder builder, String description, Consumer<Boolean> callback){

        Bot.getBot().getServers().forEach(server -> {

            ServerOptionInstance option = Bot.getServerOption(server.getId());

            if(option == null){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel game <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            else if(option.getAnnouncement_channel_id() == -1 && option.getGame_announcement_channel_id() == -1){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel game <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            else {
                long announcement_channel_id = option.getGame_announcement_channel_id() == -1 ? option.getAnnouncement_channel_id() : option.getGame_announcement_channel_id();

                server.getTextChannelById(announcement_channel_id).ifPresent(channel -> {
                    builder.setDescription(description);
                    channel.sendMessage(builder);
                });
            }

        });

        callback.accept(true);

    }

    private static void sendEmbedOnEveryServerBazaar(EmbedBuilder builder, String description, Consumer<Boolean> callback){

        Bot.getBot().getServers().forEach(server -> {

            ServerOptionInstance option = Bot.getServerOption(server.getId());

            if(option == null){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel bazaar <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            else if(option.getAnnouncement_channel_id() == -1 && option.getBazaar_announcement_channel_id() == -1){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel bazaar <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            else {
                long announcement_channel_id = option.getBazaar_announcement_channel_id() == -1 ? option.getAnnouncement_channel_id() : option.getBazaar_announcement_channel_id();

                server.getTextChannelById(announcement_channel_id).ifPresent(channel -> {
                    builder.setDescription(description);
                    channel.sendMessage(builder);
                });
            }

        });

        callback.accept(true);

    }

    private static void sendEmbedOnEveryServerTeam(EmbedBuilder builder, String description, Consumer<Boolean> callback){

        Bot.getBot().getServers().forEach(server -> {

            ServerOptionInstance option = Bot.getServerOption(server.getId());

            if(option == null){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel bazaar <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            if(option.getAnnouncement_channel_id() == -1 && option.getTeam_announcement_channel_id() == -1){
                for(ServerTextChannel channel : server.getTextChannels()){
                    if(channel.asPrivateChannel().isPresent())
                        continue;
                    builder.setDescription(description + "\n\nPro administrátory serveru\nTato zpráva byla poslána do místnosti, do které mají všichni přistup, jelikož nebyla nastavena místnost pro automatické zprávy, kterou nastavíte pomocí `/channel bazaar <označení místnosti>`");
                    channel.sendMessage(builder);
                    break;
                }
            }
            else {
                long announcement_channel_id = option.getTeam_announcement_channel_id() == -1 ? option.getAnnouncement_channel_id() : option.getTeam_announcement_channel_id();

                server.getTextChannelById(announcement_channel_id).ifPresent(channel -> {
                    builder.setDescription(description);
                    channel.sendMessage(builder);
                });
            }

        });

        callback.accept(true);

    }

}
