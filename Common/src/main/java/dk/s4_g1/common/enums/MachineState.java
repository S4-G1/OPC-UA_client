package dk.s4_g1.common.enums;

import java.util.Optional;

public enum MachineState {
  DEACTIVATED(0),
  CLEARING(1),
  STOPPED(2),
  STARTING(3),
  IDLE(4),
  SUSPENDED(5),
  EXECUTE(6),
  STOPPING(7),
  ABORTING(8),
  ABORTED(9),
  HOLDING(10),
  HELD(11),
  RESETTING(15),
  COMPLETING(16),
  COMPLETE(17),
  DEACTIVATING(18),
  ACTIVATING(19);

  public final int id;

  private MachineState(int id) {
    this.id = id;
  }

  public static Optional<MachineState> getStateFromValue(int id) {
    for (MachineState state : MachineState.values()) {
      if (state.id == id) {
        return Optional.of(state);
      }
    }
    return Optional.empty();
  }
}
