package dk.s4_g1.common.enums;

import java.util.Optional;

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

    public static Optional<Commands> getCommand(String name) {
        for (Commands command : Commands.values()) {
            if (name.equalsIgnoreCase(command.name())) {
                return Optional.of(command);
            }
        }
        return Optional.empty();
    }
}
