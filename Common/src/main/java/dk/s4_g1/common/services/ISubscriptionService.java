package dk.s4_g1.common.services;

import dk.s4_g1.common.enums.Nodes;

public interface ISubscriptionService {

    public ISubscription subscribe(Nodes node);

    public boolean stopSubscription(Nodes node);
}
