package dk.s4_g1.common.enums;

import java.util.Optional;

public enum StopReasons {

    EMPTY_INVENTORY(10),
    MAINTENANCE_NEEDED(11),
    MANUAL_STOP(12),
    MOTOR_POWER_LOSS(13),
    MANUAL_ABORT(14);

    public final int id;

    private StopReasons(int id) {
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
