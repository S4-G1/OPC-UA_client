package dk.s4_g1.common.enums;

public enum Commands {
    RESET(1),
    START(2),
    STOP(3),
    ABORT(4),
    CLEAR(5);

    public final int id;

    private Commands(int id) {
        this.id = id;
    }
}
