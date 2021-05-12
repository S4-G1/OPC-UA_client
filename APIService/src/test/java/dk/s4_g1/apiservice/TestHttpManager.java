package dk.s4_g1.apiservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dk.s4_g1.common.data.Response;
import org.junit.jupiter.api.*;

class TestHttpManager {

  HttpManager manager = new HttpManager();
  private String endpoint = "inventory_statuses";

  @Test
  void post() {
    // Mock HttpManager Object
    HttpManager mockManager = mock(HttpManager.class);

    // Create json String
    String json =
        """
            {
                "name": "Pilsner",
                "max_value": 200,
                "current_value": 69
            }
        """;

    // Setup rule that returns new Response when manager.post is called
    when(mockManager.post(endpoint, json)).thenReturn(new Response(201, json));

    Response r = mockManager.post(endpoint, json);
    assertEquals(201, r.statusCode);
    assertEquals(json, r.body);
    verify(mockManager).post(endpoint, json);
  }

  @Test
  void getValidEndpoint() {
    Response r = manager.get(endpoint);

    assertEquals(200, r.statusCode);
  }

  @Test
  void getInvalidEndpoint() {
    Response r = manager.get("Invalid");

    assertEquals(404, r.statusCode);
  }

  @Test
  void endpointFormat() {
    assertEquals(
        "https://api.bierproductie.nymann.dev/inventory_statuses",
        manager.endpointFormat(endpoint));
  }
}
