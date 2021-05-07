package dk.s4_g1.common.enums;

import java.util.Optional;

public enum StopReasons {

    EMPTY_INVENTORY("Empty Inventory", 10),
    MAINTENANCE_NEEDED("Maintenance Needed", 11),
    MANUAL_STOP("Manual Stop", 12),
    MOTOR_POWER_LOSS("Motor Power Loss", 13),
    MANUAL_ABORT("Manual Abort", 14);

    public final int id;
    public final String reason;

    private StopReasons(String reason, int id) {
        this.reason = reason;
        this.id = id;
    }

    public static Optional<StopReasons> getStopReasonsFromId(int id){
        for(StopReasons reason : StopReasons.values()){
            if (reason.id == id) {
                return Optional.of(reason);
            }
        }

        return Optional.empty();
    }

}
