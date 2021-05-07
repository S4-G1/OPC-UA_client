package dk.s4_g1.opcuaservice;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.services.ICommandService;

public class CommandManager implements ICommandService{

    private OpcUaClient client;

    public CommandManager(){
        try{
            //Create list of endpoints
            List<EndpointDescription> endpoints =  DiscoveryClient.getEndpoints("opc.tcp://127.0.0.1:4840").get();
            //Create config builder
            OpcUaClientConfigBuilder configBuilder = new OpcUaClientConfigBuilder().setIdentityProvider(new UsernameProvider("sdu", "1234"));
            //Set endpoint
            configBuilder.setEndpoint(endpoints.get(0));

            client = OpcUaClient.create(configBuilder.build());
            client.connect().get();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendCmdBool(Nodes node, boolean value) {
        var dataValue = createDataValue(new Variant(value));
        if(dataValue == null){
            return false;
        }

        return writeValue(node, dataValue);
    }

    @Override
    public boolean sendCmdFloat(Nodes node, float value) {
        var dataValue = createDataValue(new Variant(value));
        if(dataValue == null){
            return false;
        }

        return writeValue(node, dataValue);
    }

    @Override
    public boolean sendCmdInt(Nodes node, int value) {
        var dataValue = createDataValue(new Variant(value));
        if(dataValue == null){
            return false;
        }
        
        return writeValue(node, dataValue);
    }


    private DataValue createDataValue(Variant v){
        try{
            return DataValue.valueOnly(v);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private NodeId createNodeId(Nodes type){
        return new NodeId(type.namespaceIndex, type.node);
    }

    private boolean writeValue(Nodes node, DataValue dv){
        try{
            client.writeValue(createNodeId(node), dv).get();
        } catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
