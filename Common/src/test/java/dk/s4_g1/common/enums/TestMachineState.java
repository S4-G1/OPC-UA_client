package dk.s4_g1.common.enums;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

class TestMachineState {

    @Test
    void testEnumsHasId(){
        assertEquals(0, MachineState.DEACTIVATED.id);
        assertEquals(1, MachineState.CLEARING.id);
        assertEquals(2, MachineState.STOPPED.id);
        assertEquals(3, MachineState.STARTING.id);
        assertEquals(4, MachineState.IDLE.id);
        assertEquals(5, MachineState.SUSPENDED.id);
        assertEquals(6, MachineState.EXECUTE.id);
        assertEquals(7, MachineState.STOPPING.id);
        assertEquals(8, MachineState.ABORTING.id);
        assertEquals(9, MachineState.ABORTED.id);
        assertEquals(10, MachineState.HOLDING.id);
        assertEquals(11, MachineState.HELD.id);

        assertEquals(15, MachineState.RESETTING.id);
        assertEquals(16, MachineState.COMPLETING.id);
        assertEquals(17, MachineState.COMPLETE.id);
        assertEquals(18, MachineState.DEACTIVATING.id);
        assertEquals(19, MachineState.ACTIVATING.id);
    }

    @Test
    void testGetStateFromValue(){
        assertEquals(Optional.empty(), MachineState.getStateFromValue(12), "it should be empty");
        assertEquals(Optional.of(MachineState.DEACTIVATED), MachineState.getStateFromValue(0));
        assertEquals(Optional.of(MachineState.CLEARING), MachineState.getStateFromValue(1));
        assertEquals(Optional.of(MachineState.STOPPED), MachineState.getStateFromValue(2));
        assertEquals(Optional.of(MachineState.STARTING), MachineState.getStateFromValue(3));
        assertEquals(Optional.of(MachineState.IDLE), MachineState.getStateFromValue(4));
        assertEquals(Optional.of(MachineState.SUSPENDED), MachineState.getStateFromValue(5));
        assertEquals(Optional.of(MachineState.EXECUTE), MachineState.getStateFromValue(6));
        assertEquals(Optional.of(MachineState.STOPPING), MachineState.getStateFromValue(7));
        assertEquals(Optional.of(MachineState.ABORTING), MachineState.getStateFromValue(8));
        assertEquals(Optional.of(MachineState.ABORTED), MachineState.getStateFromValue(9));
        assertEquals(Optional.of(MachineState.HOLDING), MachineState.getStateFromValue(10));
        assertEquals(Optional.of(MachineState.HELD), MachineState.getStateFromValue(11));

        assertEquals(Optional.of(MachineState.RESETTING), MachineState.getStateFromValue(15));
        assertEquals(Optional.of(MachineState.COMPLETING), MachineState.getStateFromValue(16));
        assertEquals(Optional.of(MachineState.COMPLETE), MachineState.getStateFromValue(17));
        assertEquals(Optional.of(MachineState.DEACTIVATING), MachineState.getStateFromValue(18));
        assertEquals(Optional.of(MachineState.ACTIVATING), MachineState.getStateFromValue(19));
    }
}
