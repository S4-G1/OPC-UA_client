package dk.s4_g1.common.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestCommands {

  @Test
  void testEnumsHasTheRightID() {
    assertEquals(1, Commands.RESET.id);
    assertEquals(2, Commands.START.id);
    assertEquals(3, Commands.STOP.id);
    assertEquals(4, Commands.ABORT.id);
    assertEquals(5, Commands.CLEAR.id);
  }
}
