package dk.s4_g1.logger;

public class Main {

    public static void main(String[] args) {
        Logger logger = new Logger();

        logger.Debug(Logger.class.toString(), "This is a stupid message, and Kevin made me write a longer one");
    }
}
