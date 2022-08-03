public class SoftBot {

    public static void main(String args[]){

        Utils.LogSystem.log("PROGRAM", "Program started. Initializing...", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        Utils.Bot.initializeBot();
        Utils.LogSystem.log("PROGRAM", "Initialize finished", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }

}
