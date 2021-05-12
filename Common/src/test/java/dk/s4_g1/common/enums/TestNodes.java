package dk.s4_g1.common.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestNodes {

  @Test
  void testNameSpaceIndex() {
    assertEquals(6, Nodes.STATE.namespaceIndex);
  }

  @Test
  void testCommandNode() {
    assertEquals("::Program:Cube.Command.MachSpeed", Nodes.NEXT_MACHINE_SPEED.node);
    assertEquals("::Program:Cube.Command.CntrlCmd", Nodes.NEXT_MACHINE_COMMAND.node);
    assertEquals("::Program:Cube.Command.Parameter[0].Value", Nodes.NEXT_BATCH_ID.node);
    assertEquals("::Program:Cube.Command.Parameter[1].Value", Nodes.NEXT_PRODUCT_ID_FOR.node);
    assertEquals("::Program:Cube.Command.Parameter[2].Value", Nodes.NEXT_PRODUCT_AMOUNT.node);
    assertEquals("::Program:Cube.Command.CmdChangeRequest", Nodes.EXECUTE_MACHINE_CMD.node);
  }

  void testStatusNode() {
    assertEquals("::Program:Cube.Admin.ProdProcessedCount", Nodes.PRODUCED_PRODUCTS.node);
    assertEquals("::Program:Cube.Admin.ProdDefectiveCount", Nodes.DEFECTIVE_PRODUCTS.node);
    assertEquals("::Program:Cube.Admin.StopReason.ID", Nodes.STOP_REASON_ID.node);
    assertEquals("::Program:Cube.Admin.StopReason.Value", Nodes.STOP_REASON_VALUE.node);
    assertEquals("::Program:Cube.Admin.Parameter[0].Value", Nodes.BATCH_PRODUCT_ID.node);

    assertEquals("::Program:Cube.Status.StateCurrent", Nodes.STATE.node);
    assertEquals("::Program:Cube.Status.MachSpeed", Nodes.SPEED.node);
    assertEquals("::Program:Cube.Status.CurMachSpeed", Nodes.NORMALIZED_SPEED.node);
    assertEquals("::Program:Cube.Status.Parameter[0].Value", Nodes.BATCH_ID.node);
    assertEquals("::Program:Cube.Status.Parameter[1].Value", Nodes.PRODUCT_TO_AMOUNT.node);

    assertEquals("::Program.Inventory.Barley", Nodes.STATUS_BARLEY.node);
    assertEquals("::Program.Inventory.Malt", Nodes.STATUS_MALT.node);
    assertEquals("::Program.Inventory.Hops", Nodes.STATUS_HOPS.node);
    assertEquals("::Program.Inventory.Wheat", Nodes.STATUS_WHEAT.node);
    assertEquals("::Program.Inventory.Yeast", Nodes.STATUS_YEAST.node);
    assertEquals("::Program.Maintenance", Nodes.STATUS_MAINTENANCE.node);
    assertEquals("::Program:Cube.Status.Parameter[2].Value", Nodes.STATUS_HUMIDITY.node);
    assertEquals("::Program:Cube.Status.Parameter[3].Value", Nodes.STATUS_TEMPERATURE.node);
    assertEquals("::Program:Cube.Status.Parameter[4].Value", Nodes.STATUS_VIBRATION.node);
  }
}
