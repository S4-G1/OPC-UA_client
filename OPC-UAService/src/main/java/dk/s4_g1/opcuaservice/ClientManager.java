package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.services.*;
import dk.s4_g1.common.util.*;
import dk.s4_g1.common_opcua.services.IOpcUaClientService;

import org.apache.logging.log4j.*;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;

import java.util.concurrent.ExecutionException;

public class ClientManager implements IOpcUaClientService {

    private OpcUaClient client;
    private static Logger logger = LogManager.getLogger(ClientManager.class);

    public ClientManager() throws ServiceLoaderException {
        var optionalConfigLoader = ServiceLoader.getDefault(IConfigService.class);
        if (optionalConfigLoader.isEmpty()) {
            logger.error("Can't loaded Configloader");
            throw new ServiceLoaderException("IconfigService can't be loaded");
        }
        var config = optionalConfigLoader.get();

        var opcUaClientConfig = getOpcUaClientConfig(config, getEndpoint(config));

        logger.info(
                "Got endpoint url from OPC-UA server: {}",
                opcUaClientConfig.getEndpoint().getEndpointUrl().toString());

        try {
            client = OpcUaClient.create(opcUaClientConfig);
            client.connect().get();
        } catch (ExecutionException | UaException | InterruptedException e) {
            logger.error("OpcUaClient can't connect");
            Thread.currentThread().interrupt();
            throw new ServiceLoaderException("OpcUaClient can't connect to the machine");
        }

        logger.info(
                "IOpcUaClientService - ClientManager Creadted and connected to {}",
                client.getConfig().getEndpoint().getEndpointUrl());
    }

    protected EndpointDescription getEndpoint(IConfigService config) throws ServiceLoaderException {
        var endpointUrl = config.getConfig("BEER_URL").orElse("127.0.0.1");
        var endpointPort = config.getConfig("BEER_PORT").orElse("4840");
        var endpointUrlPort = "opc.tcp://" + endpointUrl + ":" + endpointPort;
        logger.info("Trying to connect to endpoint: {}", endpointUrlPort);
        try {
            var endpointDescription = DiscoveryClient.getEndpoints(endpointUrlPort).get().get(0);
            return EndpointUtil.updateUrl(
                    endpointDescription, endpointUrl, Integer.parseInt(endpointPort));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("OpcUaClient can't get a list of endpoints");
            Thread.currentThread().interrupt();
            throw new ServiceLoaderException("OpcUaClient can't get a list of endpoints");
        }
    }

    protected static OpcUaClientConfig getOpcUaClientConfig(
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
}
