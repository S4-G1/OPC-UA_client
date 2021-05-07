package dk.s4_g1.apiservice;

import org.junit.jupiter.api.Test;

class TestHttpManager {

    HttpManager manager;

    @BeforeEach
    void setUp(){
        manager = mock(HttpManager.class);
    }
}