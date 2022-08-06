import Enums.LogTypeEnum;

public class SoftBot {

    public static void main(String args[]){

        Utils.LogSystem.log(LogTypeEnum.INFO, "Program started. Initializing...", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        Utils.Bot.initializeBot();
        Utils.LogSystem.log(LogTypeEnum.INFO, "Initialize finished", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }

}
