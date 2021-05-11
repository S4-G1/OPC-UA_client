package dk.s4_g1.opcuaservice;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.services.ICommandService;

public class CommandManager implements ICommandService{

    private OpcUaClient client;
    private static Logger logger = LogManager.getLogger();

    public CommandManager() throws InterruptedException {
        //Create list of endpoints
        List<EndpointDescription> endpoints = null;
        try {
            endpoints =  DiscoveryClient.getEndpoints("opc.tcp://127.0.0.1:4840").get();
        } catch(ExecutionException e){
            logger.error("OpcUaClient can't connect: {}", e);
            Thread.currentThread().interrupt();
        }

        //catch(ExecutionException e){}
        //Create config builder
        OpcUaClientConfigBuilder configBuilder = new OpcUaClientConfigBuilder().setIdentityProvider(new UsernameProvider("sdu", "1234"));
        //Set endpoint
        configBuilder.setEndpoint(endpoints.get(0));

        try {
            client = OpcUaClient.create(configBuilder.build());
        } catch(UaException e){
            logger.error("can't create a OpcUaClient: {}", e);
            return;
        }

        try {
            client.connect().get();
        } catch(ExecutionException e){
            logger.error("OpcUaClient can't connect: {}", e);
            Thread.currentThread().interrupt();
        }
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

    private NodeId createNodeId(Nodes type){
        return new NodeId(type.namespaceIndex, type.node);
    }

    private boolean writeValue(Nodes node, Variant variant){
        var dataValue = DataValue.valueOnly(variant);
        try{
            client.writeValue(createNodeId(node), dataValue).get();
        } catch(InterruptedException | ExecutionException e){
            logger.error("Can't write value: {}", e);
            Thread.currentThread().interrupt();
        }

        return true;
    }
}
