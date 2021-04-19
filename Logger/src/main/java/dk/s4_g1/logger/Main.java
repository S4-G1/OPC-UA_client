package dk.s4_g1.logger;


import java.util.ServiceLoader;
import dk.s4_g1.common.services.ILog;

public class Main {

    public static void main(String[] args) {
        ServiceLoader<ILog> loader = ServiceLoader.load(ILog.class);

        for(ILog log : loader) {
            log.Info(Logger.class.toString(), "This is a stupid message, and Kevin made me write a longer one");
            log.Debug(Logger.class.toString(), "This is a stupid message, and Kevin made me write a longer one");
            log.Warning(Logger.class.toString(), "This is a stupid message, and Kevin made me write a longer one");
            log.Error(Logger.class.toString(), "This is a stupid message, and Kevin made me write a longer one");
        }
    }
}
