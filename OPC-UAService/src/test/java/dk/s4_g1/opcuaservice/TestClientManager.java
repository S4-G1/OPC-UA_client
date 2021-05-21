package dk.s4_g1.opcuaservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dk.s4_g1.config_service.ConfigManager;

import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class TestClientManager {

    // @Test
    // void CreateConfigMangerWithJavaServiceLoader() {
    //     assertThrows(
    //             ServiceLoaderException.class,
    //             () -> {
    //                 new ClientManager();
    //             });
    // }

    @Test
    void TestgetOpcUaClientConfig() {
        var config = mock(ConfigManager.class);
        when(config.getConfig("BEER_USER")).thenReturn(Optional.empty());

        var endpoint = mock(EndpointDescription.class);

        var opcUaConfig = new OpcUaClientConfigBuilder().setEndpoint(endpoint).build();
        assertEquals(
                opcUaConfig.getEndpoint().getEndpointUrl(),
                ClientManager.getOpcUaClientConfig(config, endpoint)
                        .getEndpoint()
                        .getEndpointUrl());

        when(config.getConfig("BEER_USER")).thenReturn(Optional.of("sdu"));
        when(config.getConfig("BEER_PASSWORD")).thenReturn(Optional.of("1234"));

        var opcUaConfigWithIdentityProvider =
                new OpcUaClientConfigBuilder()
                        .setIdentityProvider(new UsernameProvider("sdu", "1234"))
                        .setEndpoint(endpoint)
                        .build();

        assertEquals(
                opcUaConfigWithIdentityProvider.getIdentityProvider().toString(),
                ClientManager.getOpcUaClientConfig(config, endpoint)
                        .getIdentityProvider()
                        .toString());

        assertNotEquals(
                opcUaConfig.getIdentityProvider().toString(),
                opcUaConfigWithIdentityProvider.getIdentityProvider().toString());
    }
}
