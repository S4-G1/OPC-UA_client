package dk.s4_g1.config_service;

import dk.s4_g1.common.services.IConfigService;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import org.apache.logging.log4j.*;

public class ConfigManager implements IConfigService {

    private static Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Marker CONFIG = MarkerManager.getMarker("CONFIG");
    private HashMap<String, String> keyset;


    public ConfigManager(){
        keyset = new HashMap<>();
        keyset.put("API_URL", "https://api.bierproductie.nymann.dev");
        keyset.put("BEER_URL", "opc.tcp://127.0.0.1:4840");
        keyset.put("BEER_PASSWORD", "1234");
        keyset.put("BEER_USER", "sdu");
        logger.info(CONFIG, "ConfigManger Created");
    }

    @Override
    public Optional<String> getConfig(String key){
        String variable = getEnv(key);
        if (variable != null) {
            logger.info(CONFIG, "Loaded key: {} from enviromt variable. Key is: {}", key, variable);
            return Optional.of(variable);
        }

        variable = variableByFile(key);
        if (variable != null) {
            logger.info(CONFIG, "Loaded key: {} from file. Key is: {}", key, variable);
            return Optional.of(variable);
        }

        variable = keyset.get(key);
        if (variable != null) {
            logger.info(CONFIG, "Loaded key: {} from ConfigManager. Key is: {}", key, variable);
            return Optional.of(variable);
        }

        logger.error(CONFIG, "Tried to load key: {}, Does not exites", key);
        return Optional.empty();
    }

    protected String getEnv(String key) {
        return System.getenv(key);
    }

    private String variableByFile(String key) {
        try (var reader = new Scanner(new File("config"))){
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                var linesplit = line.split("=");
                var varialbe = linesplit[1].trim();
                if (linesplit[0].trim().matches(key)){
                    if (varialbe.startsWith("\"")) {
                        return varialbe.substring(1, varialbe.length()-1);
                    }
                    return varialbe;
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn("Config file not founded");
        }
        return null;
    }

}