package dk.s4_g1.common.services;

import dk.s4_g1.common.enums.Nodes;

public interface ISubscriptionService {

    public boolean subscribe(Nodes node);

    public boolean stopSubscription(Nodes node);

    public boolean stopAllSubscription();

    public void addCallback(ICallbackSubscription callback);
}
