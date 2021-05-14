package dk.s4_g1.common.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class TestStopReasons {

    @Test
    void TestIdStopReasons() {
        assertEquals(10, StopReasons.EMPTY_INVENTORY.id);
        assertEquals(11, StopReasons.MAINTENANCE_NEEDED.id);
        assertEquals(12, StopReasons.MANUAL_STOP.id);
        assertEquals(13, StopReasons.MOTOR_POWER_LOSS.id);
        assertEquals(14, StopReasons.MANUAL_ABORT.id);
    }

    @Test
    void testGetStateFromValue() {
        assertEquals(Optional.empty(), StopReasons.getStopReasonsFromId(9));
        assertEquals(
                Optional.of(StopReasons.EMPTY_INVENTORY), StopReasons.getStopReasonsFromId(10));
        assertEquals(
                Optional.of(StopReasons.MAINTENANCE_NEEDED), StopReasons.getStopReasonsFromId(11));
        assertEquals(Optional.of(StopReasons.MANUAL_STOP), StopReasons.getStopReasonsFromId(12));
        assertEquals(
                Optional.of(StopReasons.MOTOR_POWER_LOSS), StopReasons.getStopReasonsFromId(13));
        assertEquals(Optional.of(StopReasons.MANUAL_ABORT), StopReasons.getStopReasonsFromId(14));
    }
}
