package dk.s4_g1.logger;

import dk.s4_g1.common.services.ILog;

public class Logger implements ILog {
    // ANSI terminal color, does not work in windows CMD. ANSI terminal color
    // works in powershell, and linux shells. 
    // More on colors in the terminal can be found here.
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[1;31m";
    private static final String ANSI_GREEN = "\u001B[1;32m";
    private static final String ANSI_YELLOW = "\u001B[1;33m";
    private static final String ANSI_BLUE = "\u001B[1;34m";

    @Override
    public void Debug(String className, String msg) {
        System.err.println(colorString("[DEBUG]", ANSI_GREEN) + " <" + className + "> " + msg);

        // println!("\x1b[1;31m{}. Delete A tmux session\x1b[m", index);
    }

    @Override
    public void Error(String className, String msg) {
        System.err.println(colorString("[ERROR]", ANSI_RED) + " <" + className + "> " + msg);
    }

    @Override
    public void Warning(String className, String msg) {
        System.err.println(colorString("[WARNING]", ANSI_YELLOW) + " <" + className + "> " + msg);
    }

    @Override
    public void Info(String className, String msg) {
        System.out.println(colorString("[INFO]", ANSI_BLUE) + " <" + className + "> " + msg);
    }

    private String colorString(String src, String ansi_code) {
        return ansi_code + src + ANSI_RESET;
    }
}
