package dk.s4_g1.common.util;

import java.util.Optional;

public class ServiceLoader {
    private ServiceLoader() {}

    public static <T> Optional<T> getDefault(Class<T> service) {
        return java.util.ServiceLoader.load(service).findFirst();
    }
}
