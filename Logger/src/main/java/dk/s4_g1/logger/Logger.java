package dk.s4_g1.logger;

import dk.s4_g1.common.services.ILog;

public class Logger implements ILog{
    @Override
    public void Debug(Class c, String msg) {
        System.err.println("Debug<" + c.getName() + "> " + msg);
    }

    @Override
    public void Error(String msg) {
        System.err.println(msg);
    }

    @Override
    public void Warning(String msg) {
        System.err.println(msg);
    }

    @Override
    public void Info(String msg) {
        System.out.println(msg);
    }
}
