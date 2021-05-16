package dk.s4_g1.opcuaservice;

import static org.junit.jupiter.api.Assertions.*;

import dk.s4_g1.commonOpcUa.service.IOpcUaClientService;

import org.junit.jupiter.api.Test;

class TestClientManager {

    // @Test
    // void CreateConfigManger() {
    //     assertDoesNotThrow(
    //             () -> {
    //                 var client = new ClientManager();
    //                 assertNotNull(client);
    //             });
    // }

    @Test
    void CreateConfigMangerWithJavaServiceLoader() {
        assertDoesNotThrow(
                () -> {
                    var optionalClient =
                            java.util.ServiceLoader.load(IOpcUaClientService.class).findFirst();
                    assertTrue(optionalClient.isPresent());
                });
    }
}
