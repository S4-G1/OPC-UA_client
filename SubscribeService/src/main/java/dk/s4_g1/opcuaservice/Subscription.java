package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.services.*;

import org.apache.logging.log4j.*;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

public class Subscription<T> implements ISubscription {
    T t;
    private static Logger logger = LogManager.getLogger(SubscribtionManager.class);

    public Subscription(T t) {
        this.t = t;
    }

    protected void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        logger.info(
                "subscription value received: item={}, value={}",
                item.getReadValueId().getNodeId(),
                value.getValue());
    }

    @Override
    public void run() {
        // TODO
    }

    @Override
    public T getValue() {
        return t;
    }
}
