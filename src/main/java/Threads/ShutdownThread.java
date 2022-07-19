package Threads;

import Utils.Bot;
import Utils.LogSystem;

public class ShutdownThread extends Thread {

    @Override
    public void run()
    {

        Utils.LogSystem.log(Bot.getPrefix(), "exiting application", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        Bot.getBot().disconnect();
        LogSystem.saveLogs();

    }

}
