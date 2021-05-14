package dk.s4_g1.config_service;

import dk.s4_g1.common.services.IConfigService;

import org.apache.logging.log4j.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class ConfigManager implements IConfigService {

    private static Logger logger = LogManager.getLogger(ConfigManager.class);
    private Map<String, String> keyset;

    public ConfigManager() {
        keyset =
                new HashMap<>(
                        Map.of(
                                "API_URL", "https://api.bierproductie.nymann.dev",
                                "BEER_URL", "opc.tcp://127.0.0.1:4840",
                                "BEER_USER", "sdu",
                                "BEER_PASSWORD", "1234"));
        logger.info("IConfigService - ConfigManger Created");
    }

    @Override
    public Optional<String> getConfig(String key) {
        String variable = getEnv(key);
        if (variable != null) {
            logger.info("Loaded key: {} from enviromt variable. Key is: {}", key, variable);
            return Optional.of(variable);
        }

        variable = variableByFile(key);
        if (variable != null) {
            logger.info("Loaded key: {} from file. Key is: {}", key, variable);
            return Optional.of(variable);
        }

        variable = keyset.get(key);
        if (variable != null) {
            logger.info("Loaded key: {} from ConfigManager. Key is: {}", key, variable);
            return Optional.of(variable);
        }

        logger.error("Tried to load key: {}, Does not exites", key);
        return Optional.empty();
    }

    protected String getEnv(String key) {
        return System.getenv(key);
    }

    private String variableByFile(String key) {
        try (var reader = new Scanner(new File("config"))) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (!line.contains("=")) {
                    continue;
                }

                var linesplit = line.split("=");
                var variable = linesplit[1].trim();
                if (linesplit[0].trim().matches(key)) {
                    if (variable.startsWith("\"")) {
                        return variable.substring(1, variable.length() - 1);
                    }
                    return variable;
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn("Config file not founded");
        }
        return null;
    }
}
