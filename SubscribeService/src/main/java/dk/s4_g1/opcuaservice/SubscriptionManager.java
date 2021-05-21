package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.services.*;
import dk.s4_g1.common.util.*;
import dk.s4_g1.common_opcua.NodeHelper;
import dk.s4_g1.common_opcua.services.IOpcUaClientService;

import org.apache.logging.log4j.*;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.UaException;

import java.util.HashMap;

public class SubscriptionManager implements ISubscriptionService {
    private ManagedSubscription subscriptions;
    private static Logger logger = LogManager.getLogger(SubscriptionManager.class);
    private HashMap<Nodes, ManagedDataItem> subs;

    public SubscriptionManager() throws UaException {
        subs = new HashMap<>();
        var optionalClient = ServiceLoader.getDefault(IOpcUaClientService.class);
        if (optionalClient.isEmpty()) {
            throw new RuntimeException(
                    "Can't create instance of SubscribtionManager.class, because"
                            + " IOpcUaCLientService can't be loaded");
        }

        // Throws UaException bad code. I hate Exceptions... need better error
        // handling. Handling exceptions not pretty.
        subscriptions = ManagedSubscription.create(optionalClient.get().getClient());

        logger.info("ISubscriptionService - SubscribtionManager Creadted");
    }

    @Override
    public boolean subscribe(Nodes node, ICallbackSubscription callback) {
        subscriptions.addDataChangeListener(
                (items, values) -> {
                    for (int i = 0; i < items.size(); i++) {
                        logger.info(
                                "subscription value received: item={}, value={}",
                                items.get(i).getNodeId().toString(),
                                values.get(i).getValue().toString());
                        Nodes n = Nodes.getNodeFromString(items.get(i).getNodeId().toString());
                        callback.sendMsg(n, values.get(i).getValue().toString());
                    }
                });
        
        ManagedDataItem dataItem = null;
        try {
            dataItem = subscriptions.createDataItem(NodeHelper.createNodeId(node));
        } catch (UaException e) {
            logger.error("Could not create subscription on node: {}", node);
            return false;
        }

        if (dataItem.getStatusCode().isGood()) {
            logger.info("item created for nodeId={}", dataItem.getNodeId());
            subs.put(node, dataItem);
            return true;
        }
        logger.warn(
                "failed to create item for nodeId={} (status={})",
                dataItem.getNodeId(),
                dataItem.getStatusCode());
        return false;
    }

    @Override
    public boolean stopSubscription(Nodes node) {
        if (subs.containsKey(node)) {
            try {
                subs.get(node).delete();
            } catch (UaException e) {
                logger.error(
                        "Had trouble deleting/stopping node: {} not sure if it is gone?", node);
                return false;
            }
            logger.info("Removed node {}", node);
            return true;
        }
        logger.info("Could not find node {}", node);
        return false;
    }

    public boolean stopAllSubscription() {
        subs.forEach(
                (node, subscription) -> {
                    try {
                        subscription.delete();
                    } catch (UaException e) {
                        logger.error(
                                "Had trouble deleting/stopping node: {} not sure if it is gone?",
                                node);
                    }
                });
        return true;
    }
}
