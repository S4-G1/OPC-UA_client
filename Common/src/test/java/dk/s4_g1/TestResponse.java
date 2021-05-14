package dk.s4_g1;

import static org.junit.jupiter.api.Assertions.*;

import dk.s4_g1.common.data.Response;

import org.junit.jupiter.api.Test;

class TestResponse {

    @Test
    void isOK() {
        Response r = new Response(201, "Response object created");

        assertTrue(r.isOK());
    }

    @Test
    void isNotOK() {
        Response r1 = new Response(300, "Response object created");
        Response r2 = new Response(199, "Response object created");

        assertFalse(r1.isOK());
        assertFalse(r2.isOK());
    }
}
