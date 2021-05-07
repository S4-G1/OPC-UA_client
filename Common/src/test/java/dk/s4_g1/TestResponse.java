package dk.s4_g1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dk.s4_g1.common.data.Response;

public class TestResponse {
    
    @Test
    void isOK(){
        Response r = new Response(201, "Response object created");

        assertTrue(r.isOK());
    }

    @Test
    void isNotOK(){
        Response r = new Response(300, "Response object created");

        assertFalse(r.isOK());
    }
}
