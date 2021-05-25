package dk.s4_g1.common.services;

import dk.s4_g1.common.enums.Nodes;

public interface ICallbackSubscription {
    public void sendMsg(Nodes node, String msg, String timestamp);
    public void setBatch(int id);
}
