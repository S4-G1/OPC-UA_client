package dk.s4_g1.opcuaservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.s4_g1.common.util.ServiceLoader;
import dk.s4_g1.common.util.ServiceLoaderException;
import dk.s4_g1.common_opcua.services.IOpcUaClientService;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

class TestCommandManager {

    // @Test
    // void TestCanCreateCommandManager() {
    //     assertThrows(
    //             ServiceLoaderException.class,
    //             () -> {
    //                 new CommandManager();
    //             });
    // }

    @Test
    void TestCanCreateCommandManagerWithMock() {

        try (MockedStatic<ServiceLoader> utilities = Mockito.mockStatic(ServiceLoader.class)) {
            var opcClient = mock(IOpcUaClientService.class);
            utilities
                    .when(() -> ServiceLoader.getDefault(IOpcUaClientService.class))
                    .thenReturn(Optional.of(opcClient));

            assertEquals(
                    Optional.of(opcClient), ServiceLoader.getDefault(IOpcUaClientService.class));

            var opc = mock(OpcUaClient.class);
            when(opcClient.getClient()).thenReturn(opc);

            assertDoesNotThrow(
                    () -> {
                        new CommandManager();
                    });

            utilities
                    .when(() -> ServiceLoader.getDefault(IOpcUaClientService.class))
                    .thenReturn(Optional.empty());

            assertThrows(
                    ServiceLoaderException.class,
                    () -> {
                        new CommandManager();
                    });
        }
    }
}
