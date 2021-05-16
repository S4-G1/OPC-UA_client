package dk.s4_g1.common.enums;

public enum Nodes {
    PRODUCED_PRODUCTS("::Program:Cube.Admin.ProdProcessedCount"),
    DEFECTIVE_PRODUCTS("::Program:Cube.Admin.ProdDefectiveCount"),
    STOP_REASON_ID("::Program:Cube.Admin.StopReason.ID"),
    STOP_REASON_VALUE("::Program:Cube.Admin.StopReason.Value"),
    BATCH_PRODUCT_ID("::Program:Cube.Admin.Parameter[0].Value"),

    NEXT_MACHINE_SPEED("::Program:Cube.Command.MachSpeed"),
    NEXT_MACHINE_COMMAND("::Program:Cube.Command.CntrlCmd"),
    NEXT_BATCH_ID("::Program:Cube.Command.Parameter[0].Value"),
    NEXT_RECIPE_TYPE("::Program:Cube.Command.Parameter[1].Value"),
    NEXT_PRODUCT_AMOUNT("::Program:Cube.Command.Parameter[2].Value"),
    EXECUTE_MACHINE_CMD("::Program:Cube.Command.CmdChangeRequest"),

    STATE("::Program:Cube.Status.StateCurrent"),
    SPEED("::Program:Cube.Status.MachSpeed"),
    NORMALIZED_SPEED("::Program:Cube.Status.CurMachSpeed"),
    BATCH_ID("::Program:Cube.Status.Parameter[0].Value"),
    PRODUCT_TO_AMOUNT("::Program:Cube.Status.Parameter[1].Value"),

    STATUS_BARLEY("::Program:Inventory.Barley"),
    STATUS_MALT("::Program:Inventory.Malt"),
    STATUS_HOPS("::Program:Inventory.Hops"),
    STATUS_WHEAT("::Program:Inventory.Wheat"),
    STATUS_YEAST("::Program:Inventory.Yeast"),
    STATUS_MAINTENANCE("::Program:Maintenance.Counter"),
    STATUS_HUMIDITY("::Program:Cube.Status.Parameter[2].Value"),
    STATUS_TEMPERATURE("::Program:Cube.Status.Parameter[3].Value"),
    STATUS_VIBRATION("::Program:Cube.Status.Parameter[4].Value");

    public final String node;
    public final int namespaceIndex = 6;

    private Nodes(String node) {
        this.node = node;
    }
}
