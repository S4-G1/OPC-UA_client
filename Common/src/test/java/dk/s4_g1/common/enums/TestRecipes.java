package dk.s4_g1.common.enums;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestRecipes {

    @Test
    public void testEnumsHasTheRightSpeed(){
        assertEquals(600, Recipes.PILSNER.speedLimit);
        assertEquals(300, Recipes.WHEAT.speedLimit);
        assertEquals(150, Recipes.IPA.speedLimit);
        assertEquals(200, Recipes.STOUT.speedLimit);
        assertEquals(100, Recipes.ALE.speedLimit);
        assertEquals(125, Recipes.ALCOHOL_FREE.speedLimit);
    }

    @Test
    public void testEnumsHasTheRightName(){
        assertEquals("Pilsner", Recipes.PILSNER.name);
        assertEquals("Wheat", Recipes.WHEAT.name);
        assertEquals("IPA", Recipes.IPA.name);
        assertEquals("Stout", Recipes.STOUT.name);
        assertEquals("Ale", Recipes.ALE.name);
        assertEquals("Alcohol Free", Recipes.ALCOHOL_FREE.name);
    }
}
