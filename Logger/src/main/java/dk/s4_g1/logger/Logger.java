package dk.s4_g1.logger;

import dk.s4_g1.common.services.ILog;

public class Logger implements ILog{
    @Override
    public void Debug(String className, String msg) {
        System.err.println("[DEBUG] <" + className + "> " + msg);
    }

    @Override
    public void Error(String className, String msg) {
        System.err.println("[ERROR] <" + className + "> " + msg);
    }

    @Override
    public void Warning(String className, String msg) {
        System.err.println("[WARNING]  <" + className + "> " + msg);
    }

    @Override
    public void Info(String className, String msg) {
        System.err.println("[INFO]  <" + className + "> " + msg);
    }
}
