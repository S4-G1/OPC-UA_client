package dk.s4_g1.config_service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigManagerTest {
    ConfigManager cm;

    private static Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Marker CONFIG = MarkerManager.getMarker("TEST--CONFIG--TEST");



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


    ConfigManagerTest(){
        cm = new ConfigManager();
    }




    @Test
    void TestLoadConfig(){
        assertEquals(Optional.of("https://api.bierproductie.nymann.dev"), cm.getConfig("API_URL"));
        assertEquals(Optional.of("opc.tcp://127.0.0.1:4840"), cm.getConfig("BEER_URL"));
        assertEquals(Optional.of("1234"), cm.getConfig("BEER_PASSWORD"));
        assertEquals(Optional.of("sdu"), cm.getConfig("BEER_USER"));
        assertEquals(Optional.empty(), cm.getConfig("BEER_USER21312912"), "Maybe there is a test config left over?");

        setupFile();
        assertEquals(Optional.of("https://api.bierproductie.nymann.dev/2"), cm.getConfig("API_URL"));
        assertEquals(Optional.of("opc.tcp://127.0.0.1:4840"), cm.getConfig("BEER_URL"));
        assertEquals(Optional.of("1234"), cm.getConfig("BEER_PASSWORD"));
        assertEquals(Optional.of("sdu"), cm.getConfig("BEER_USER"));
        assertEquals(Optional.of("Nice"), cm.getConfig("BEER_USER21312912"));
        assertEquals(Optional.empty(), cm.getConfig("BEER_USERsdsw232"));
        destroyFile();

    }

    @Test
    public void test(){
        ConfigManager cmSpy = spy(ConfigManager.class);
        when(cmSpy.getEnv("API_URL")).thenReturn("https://api.bierproductie.nymann.dev/3");
        assertEquals(Optional.of("https://api.bierproductie.nymann.dev/3"), cmSpy.getConfig("API_URL"));
        when(cmSpy.getEnv("BEER_USER21312912")).thenReturn(null);
        assertEquals(Optional.empty(), cmSpy.getConfig("BEER_USER21312912"));
    }


    private void setupFile() {
        try {
            File config = new File("config");
            if (config.createNewFile()) {
                PrintWriter writer = new PrintWriter("config", "UTF-8");
                writer.println("API_URL=https://api.bierproductie.nymann.dev/2");
                writer.println("BEER_URL=opc.tcp://127.0.0.1:4840");
                writer.println("BEER_PASSWORD=1234");
                writer.println(" BEER_USER =   \"sdu\"   ");
                writer.println("   BEER_USER21312912  = Nice  ");
                writer.close();
            }
        } catch (IOException e) {
            logger.error(CONFIG, "IO exeception. read only fs? {}", e);
        }
    }

    private void destroyFile(){
            if (!new File("config").delete()) {
                logger.error(CONFIG, "Could not delete config");
            } 
    }

    // private void setUpEnvs(){
    //     try {
    //         setEnv(Map.of("https://api.bierproductie.nymann.dev/3", "API_URL",
    //                     "opc.tcp://127.0.0.1:4840", "BEER_URL",
    //                     "1234", "BEER_PASSWORD",
    //                     "sdu", "BEER_USER"
    //                     ));
    //     } catch (Exception e) {
    //         logger.error(CONFIG, "Could not setup Enviroment variables", e);
    //     }

    // }

    //Hack from https://stackoverflow.com/questions/318239/how-do-i-set-environment-variables-from-java
    // private static void setEnv(Map<String, String> newenv) throws Exception {
    //     try {
    //         Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
    //         Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
    //         theEnvironmentField.setAccessible(true);
    //         Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
    //         env.putAll(newenv);
    //         Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
    //         theCaseInsensitiveEnvironmentField.setAccessible(true);
    //         Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
    //         cienv.putAll(newenv);
    //     } catch (NoSuchFieldException e) {
    //         Class[] classes = Collections.class.getDeclaredClasses();
    //         Map<String, String> env = System.getenv();
    //         for(Class cl : classes) {
    //             if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
    //                 Field field = cl.getDeclaredField("m");
    //                 field.setAccessible(true);
    //                 Object obj = field.get(env);
    //                 Map<String, String> map = (Map<String, String>) obj;
    //                 map.clear();
    //                 map.putAll(newenv);
    //             }
    //         }
    //     }
    // }
}
