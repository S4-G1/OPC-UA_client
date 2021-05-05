package dk.s4_g1.common.enums;

import java.util.Optional;

public enum Recipes {

    PILSNER("Pilsner", 600),
    WHEAT("Wheat", 300),
    IPA("IPA", 150),
    STOUT("Stout", 200),
    ALE("Ale", 100),
    ALCOHOL_FREE("Alcohol Free", 125);

    public final String name;
    public final int speedLimit;

    private Recipes(String name, int speedLimit) {
        this.name = name;
        this.speedLimit = speedLimit;
    }

    public static Optional<Recipes> getProduct(String name) {
        for (Recipes product : Recipes.values()) {
            if (name.equalsIgnoreCase(product.name)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }
}
