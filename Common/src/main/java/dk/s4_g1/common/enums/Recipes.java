package dk.s4_g1.common.enums;

import java.util.Optional;

public enum Recipes {
    PILSNER(600),
    WHEAT(300),
    IPA(150),
    STOUT(200),
    ALE(100),
    ALCOHOL_FREE(125);

    public final int speedLimit;

    private Recipes(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public static Optional<Recipes> getProduct(String name) {
        for (Recipes product : Recipes.values()) {
            if (name.equalsIgnoreCase(product.name())) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }
}
