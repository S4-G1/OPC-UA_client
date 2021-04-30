package dk.s4_g1.comman.enums;

public enum Commands {
    
    ZERO("Zero"),
    RESET("Reset"),
    START("Start"),
    STOP("Stop"),
    ABORT("Abort"),
    CLEAR("Clear");

    public final String cmd;

    private Commands(String cmd) {
        this.cmd = cmd;
    }

    public static Commands getCommand(String cmd) {
        for (Commands command : Commands.values()){
            if (command.cmd.equalsIgnoreCase(cmd)) {
                return command;
            }
        }
        return Commands.ZERO;
    }
}
