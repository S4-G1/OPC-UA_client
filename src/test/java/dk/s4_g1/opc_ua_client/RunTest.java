package dk.s4_g1.opc_ua_client;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RunTest {
    static int randomInt;
    static int randomInt25Higher;

    @BeforeAll
    static void setup() {
        randomInt = (int) Math.random() * 100;
        randomInt25Higher = randomInt + 25;
    }

    @Test
    @Order(1)
    void checkIntNotHigherThan100() {
        assertTrue(randomInt < 100);
    }

    @Test
    @Order(2)
    void randomIntCheckEqual25() {
        assertEquals(randomInt + 25,randomInt25Higher);
    }
}
