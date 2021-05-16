package dk.s4_g1.commonOpcUa.service;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public interface IOpcUaClientService {
    public OpcUaClient getClient();
}
