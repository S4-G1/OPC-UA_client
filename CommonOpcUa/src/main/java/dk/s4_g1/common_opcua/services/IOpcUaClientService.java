package dk.s4_g1.common_opcua.services;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public interface IOpcUaClientService {
    public OpcUaClient getClient();
}
