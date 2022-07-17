package Commands;

import org.javacord.api.event.message.MessageCreateEvent;

public class GameCommand {

    public static void run(MessageCreateEvent event){

        if(event.isPrivateMessage()){



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

    private static void list(){

    }

    private static void date(){

    }

    private static void show(){

    }

}
