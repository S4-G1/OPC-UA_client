package dk.s4_g1.common.services;

import dk.s4_g1.common.enums.Nodes;

public interface ICommandService {

  public boolean sendCmdBool(Nodes node, boolean value);

  public boolean sendCmdFloat(Nodes node, float value);

  public boolean sendCmdInt(Nodes node, int value);
}
