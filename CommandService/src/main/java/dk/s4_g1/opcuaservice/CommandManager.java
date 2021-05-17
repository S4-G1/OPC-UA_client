package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.services.*;
import dk.s4_g1.common.util.*;
import dk.s4_g1.common_opcua.NodeHelper;
import dk.s4_g1.common_opcua.services.IOpcUaClientService;

import org.apache.logging.log4j.*;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;

import java.util.concurrent.ExecutionException;

public class CommandManager implements ICommandService {

    private OpcUaClient client;
    private static Logger logger = LogManager.getLogger(CommandManager.class);

    public CommandManager() throws ServiceLoaderException {
        var optionalClient = ServiceLoader.getDefault(IOpcUaClientService.class);
        if (optionalClient.isEmpty()) {
            throw new ServiceLoaderException(
                    "Can't create instance of CommandManager.class, because IOpcUaCLientService"
                            + " can't be loaded");
        }
        this.client = optionalClient.get().getClient();

        logger.info("ICommandService - CommandManager Creadted");
    }

    @Override
    public boolean sendCmdBool(Nodes node, boolean value) {
        logger.info("Send bool({}) to node: {}", value, node.node);
        return writeValue(node, new Variant(value));
    }

    @Override
    public boolean sendCmdFloat(Nodes node, float value) {
        logger.info("Send float({}) to node: {}", value, node.node);
        return writeValue(node, new Variant(value));
    }

    @Override
    public boolean sendCmdInt(Nodes node, int value) {
        logger.info("Send int({}) to node: {}", value, node.node);
        return writeValue(node, new Variant(value));
    }

    private boolean writeValue(Nodes node, Variant variant) {
        var dataValue = DataValue.valueOnly(variant);
        try {
            client.writeValue(NodeHelper.createNodeId(node), dataValue).get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Can not send value");
            Thread.currentThread().interrupt();
            return false;
        }
        logger.debug("value sendt");
        return true;
    }
}
