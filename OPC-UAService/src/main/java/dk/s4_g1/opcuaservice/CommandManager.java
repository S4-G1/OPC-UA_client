package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.services.*;

import org.apache.logging.log4j.*;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CommandManager implements ICommandService {

    private OpcUaClient client;
    private static Logger logger = LogManager.getLogger();
    private static IConfigService configLoader;

    public CommandManager() throws InterruptedException {
        configLoader = java.util.ServiceLoader.load(IConfigService.class).findFirst().get();
        // Create list of endpoints

        String url;
        var OptionalConfigLoader = java.util.ServiceLoader.load(IConfigService.class).findFirst();
        if (OptionalConfigLoader.isPresent()) {
            url =
                    OptionalConfigLoader.get()
                            .getConfig("API_URL")
                            .orElse("https://api.bierproductie.nymann.dev");
        } else {
            url = "https://api.bierproductie.nymann.dev";
        }
        List<EndpointDescription> endpoints = null;
        try {
            endpoints = DiscoveryClient.getEndpoints(url).get();
        } catch (ExecutionException e) {
            logger.error("OpcUaClient can't connect");
            Thread.currentThread().interrupt();
            return;
        }

        // Create config builder
        OpcUaClientConfigBuilder configBuilder =
                new OpcUaClientConfigBuilder()
                        .setIdentityProvider(new UsernameProvider("sdu", "1234"));
        // Set endpoint
        configBuilder.setEndpoint(endpoints.get(0));

        try {
            client = OpcUaClient.create(configBuilder.build());
        } catch (UaException e) {
            logger.error("can't create a OpcUaClient: {}", e.getMessage());
            return;
        }

        try {
            client.connect().get();
        } catch (ExecutionException e) {
            logger.error("OpcUaClient can't connect: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }

        logger.info("ICommandService - CommandManager Creadted");
    }

    @Override
    public boolean sendCmdBool(Nodes node, boolean value) {
        return writeValue(node, new Variant(value));
    }

    @Override
    public boolean sendCmdFloat(Nodes node, float value) {
        return writeValue(node, new Variant(value));
    }

    @Override
    public boolean sendCmdInt(Nodes node, int value) {
        return writeValue(node, new Variant(value));
    }

    private NodeId createNodeId(Nodes type) {
        return new NodeId(type.namespaceIndex, type.node);
    }

    private boolean writeValue(Nodes node, Variant variant) {
        var dataValue = DataValue.valueOnly(variant);
        try {
            client.writeValue(createNodeId(node), dataValue).get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Can't write value");
            Thread.currentThread().interrupt();
            return false;
        }

        return true;
    }
}
