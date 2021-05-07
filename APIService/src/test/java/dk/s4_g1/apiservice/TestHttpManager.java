package dk.s4_g1.apiservice;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestHttpManager {

    HttpManager manager;

    @BeforeEach
    void setUp(){
        manager = mock(HttpManager.class);
    }

    @Test
    void TestConstructor(){
        //Assert that the constructor doens't throw an exception
        assertDoesNotThrow(() -> new HttpManager());
    }
}