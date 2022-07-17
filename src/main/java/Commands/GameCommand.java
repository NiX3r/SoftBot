package Commands;

import Instances.GameInstance;
import Utils.Bot;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;

public class GameCommand {

    public static void run(MessageCreateEvent event){

        if(event.isPrivateMessage()){

            switch (event.getMessage().getContent().split(" ")[2]){

                case "create":
                    create(event.getMessageAuthor().getId(), event.getMessage());
                    break;

                case "edit":
                    edit();
                    break;

                case "finish":
                    finish();
                    break;

                case "visible":
                    visible();
                    break;

                case "delete":
                    delete();
                    break;

            }

        }
        else {

            event.getServer().ifPresent(server -> {

                switch (event.getMessage().getContent().split(" ")[2]){

                    case "list":
                        list();
                        break;

                    case "date":
                        date();
                        break;

                    case "show":
                        show();
                        break;

                }

            });

        }

    }

    private static void create(long user_id, Message message){

        int cacheGame = Bot.getCache().isUserAlreadyInGameInstance(user_id);

        if(cacheGame >= 0){
            message.reply("Omlouvám se, ale již jsi vytvořil jednu hru s ID `" + cacheGame + "`. Prosím první dovyplň tuto hru a ukonči ji nebo ji smaž.");
            return;
        }

        String name = message.getContent().replace("!sb game create ", "");

        GameInstance game = new GameInstance(name, user_id);
        Bot.getCache().getGameInstances().add(game);

    }

    private static void edit(){

    }

    private static void finish(){

    }

    private static void visible(){

    }

    private static void delete(){

    }

    private static void list(){

    }

    private static void date(){

    }

    private static void show(){

    }

}
