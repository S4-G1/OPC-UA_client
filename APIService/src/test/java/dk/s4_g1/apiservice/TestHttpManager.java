package dk.s4_g1.apiservice;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import dk.s4_g1.common.data.Response;

class TestHttpManager {

    private String endpoint = "Test";

    @Test
    void TestConstructor(){
        //Assert that the constructor doens't throw an exception
        new HttpManager();
        assertDoesNotThrow(() -> new HttpManager());
    }

    @Test
    void post(){
        //Mock HttpManager Object
        HttpManager manager = mock(HttpManager.class);

        //Create json String
        String json = """
            {
                "name": "Pilsner",
                "max_value": 200,
                "current_value": 69
            }
        """;

        //Setup rule that returns new Response when manager.post is called
        when(manager.post(endpoint, json)).thenReturn(new Response(201, json));

        Response r = manager.post(endpoint, json);
        assertEquals(201, r.statusCode);
        assertEquals(json, r.body);
        verify(manager).post(endpoint, json);
    }

    @Test
    void endpointFormat(){
        HttpManager manager = new HttpManager();
        
        assertEquals("https://api.bierproductie.nymann.dev/Test", manager.endpointFormat(endpoint));
    }
}