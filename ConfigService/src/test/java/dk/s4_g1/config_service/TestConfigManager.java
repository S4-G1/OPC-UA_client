package dk.s4_g1.config_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dk.s4_g1.common.services.IConfigService;

import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Optional;

class TestConfigManager {
    ConfigManager cm;

    TestConfigManager() {
        cm = new ConfigManager();
    }

    @BeforeAll
    static void moveAlreadyConfiguredConfig() {
        File config = new File("config");
        config.renameTo(new File("config.b"));
    }

    @AfterAll
    static void moveConfigBack() {
        File config = new File("config.b");
        config.renameTo(new File("config"));
    }

    @Test
    void loadConfigManager() {
        var optionalConfigLoader = java.util.ServiceLoader.load(IConfigService.class).findFirst();
        assertTrue(optionalConfigLoader.isPresent());
    }

    @Test
    void test() {
        ConfigManager cmSpy = spy(ConfigManager.class);
        when(cmSpy.getEnv("API_URL")).thenReturn("https://api.bierproductie.nymann.dev/3");
        assertEquals(
                Optional.of("https://api.bierproductie.nymann.dev/3"), cmSpy.getConfig("API_URL"));
        when(cmSpy.getEnv("BEER_USER21312912")).thenReturn(null);
        assertEquals(Optional.empty(), cmSpy.getConfig("BEER_USER21312912"));
    }

    @Test
    void TestLoadConfig() {
        assertEquals(Optional.of("https://api.bierproductie.nymann.dev"), cm.getConfig("API_URL"));
        assertEquals(Optional.of("opc.tcp://127.0.0.1:4840"), cm.getConfig("BEER_URL"));
        assertEquals(Optional.empty(), cm.getConfig("BEER_PASSWORD"));
        assertEquals(Optional.empty(), cm.getConfig("BEER_USER"));
        assertEquals(
                Optional.empty(),
                cm.getConfig("BEER_USER21312912"),
                "Maybe there is a test config left over?");
    }
}
