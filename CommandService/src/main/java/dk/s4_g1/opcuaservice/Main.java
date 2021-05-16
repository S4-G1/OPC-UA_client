package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.enums.Nodes;

public class Main {
    public static void main(String[] args) {
        var test = new CommandManager();
        test.sendCmdFloat(Nodes.NEXT_BATCH_ID, 13);
        System.exit(0);
    }
}
