package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.services.*;
import dk.s4_g1.commonOpcUa.service.IOpcUaClientService;

import org.apache.logging.log4j.*;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.concurrent.ExecutionException;

public class ClientManager implements IOpcUaClientService {

    private OpcUaClient client;
    private static Logger logger = LogManager.getLogger(ClientManager.class);

    public ClientManager() {
        var OptionalConfigLoader = java.util.ServiceLoader.load(IConfigService.class).findFirst();
        if (OptionalConfigLoader.isEmpty()) {
            throwRuntimeException("IconfigService can't be loaded");
        }
        var config = OptionalConfigLoader.get();

        var opcUaClientConfig = getOpcUaClientConfig(config, getEndpoint(config));

        try {
            client = OpcUaClient.create(opcUaClientConfig);
            client.connect().get();
        } catch (ExecutionException | UaException | InterruptedException e) {
            logger.error("OpcUaClient can't connect");
            Thread.currentThread().interrupt();
            throwRuntimeException("OpcUaClient can't connect to the machine");
        }

        logger.info(
                "IOpcUaClientService - ClientManager Creadted and connected to {}",
                client.getConfig().getEndpoint().getEndpointUrl());
    }

    protected EndpointDescription getEndpoint(IConfigService config) {
        var endpointUrl = config.getConfig("BEER_URL").orElse("opc.tcp://127.0.0.1:4840");
        logger.info("Trying to connect to endpoint: {}", endpointUrl);
        try {
            return DiscoveryClient.getEndpoints(endpointUrl).get().get(0);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("OpcUaClient can't get a list of endpoints");
            Thread.currentThread().interrupt();
            throwRuntimeException("OpcUaClient can't get a list of endpoints");
            return null;
        }
    }

    protected OpcUaClientConfig getOpcUaClientConfig(
            IConfigService config, EndpointDescription endpoint) {
        var user = config.getConfig("BEER_USER");

        if (user.isEmpty()) {
            return new OpcUaClientConfigBuilder().setEndpoint(endpoint).build();
        }
        return new OpcUaClientConfigBuilder()
                .setIdentityProvider(
                        new UsernameProvider(
                                user.get(), config.getConfig("BEER_PASSWORD").orElse("")))
                .setEndpoint(endpoint)
                .build();
    }

    @Override
    public OpcUaClient getClient() {
        return this.client;
    }

    protected void throwRuntimeException(String msg) throws RuntimeException {
        throw new RuntimeException("Can't create instance of ClientManager.class, because " + msg);
    }
}
