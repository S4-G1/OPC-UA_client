package dk.s4_g1.common.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class TestRecipes {

    @Test
    void testEnumsHasTheRightSpeed() {
        assertEquals(600, Recipes.PILSNER.speedLimit);
        assertEquals(300, Recipes.WHEAT.speedLimit);
        assertEquals(150, Recipes.IPA.speedLimit);
        assertEquals(200, Recipes.STOUT.speedLimit);
        assertEquals(100, Recipes.ALE.speedLimit);
        assertEquals(125, Recipes.ALCOHOL_FREE.speedLimit);
    }

    @Test
    void testEnumsHasNotChangedOrdre() {
        assertEquals(0, Recipes.PILSNER.ordinal());
        assertEquals(1, Recipes.WHEAT.ordinal());
        assertEquals(2, Recipes.IPA.ordinal());
        assertEquals(3, Recipes.STOUT.ordinal());
        assertEquals(4, Recipes.ALE.ordinal());
        assertEquals(5, Recipes.ALCOHOL_FREE.ordinal());
    }

    @Test
    void testEnumsHasTheRightName() {
        assertEquals("PILSNER", Recipes.PILSNER.name());
        assertEquals("WHEAT", Recipes.WHEAT.name());
        assertEquals("IPA", Recipes.IPA.name());
        assertEquals("STOUT", Recipes.STOUT.name());
        assertEquals("ALE", Recipes.ALE.name());
        assertEquals("ALCOHOL_FREE", Recipes.ALCOHOL_FREE.name());
    }

    @Test
    void testGetProduct() {
        assertEquals(Optional.empty(), Recipes.getProduct("pilsner111"), "it should be empty");
        assertEquals(Optional.of(Recipes.PILSNER), Recipes.getProduct("pilsner"));
        assertEquals(Optional.of(Recipes.PILSNER), Recipes.getProduct("PILSNER"));
        assertEquals(Optional.of(Recipes.WHEAT), Recipes.getProduct("wheat"));
        assertEquals(Optional.of(Recipes.WHEAT), Recipes.getProduct("wheat"));
        assertEquals(Optional.of(Recipes.IPA), Recipes.getProduct("ipa"));
        assertEquals(Optional.of(Recipes.STOUT), Recipes.getProduct("stout"));
        assertEquals(Optional.of(Recipes.ALE), Recipes.getProduct("ale"));
        assertEquals(Optional.of(Recipes.ALCOHOL_FREE), Recipes.getProduct("alcohol_free"));
    }
}
