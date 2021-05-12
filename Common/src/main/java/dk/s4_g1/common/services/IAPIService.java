package dk.s4_g1.common.services;

import dk.s4_g1.common.data.Response;

public interface IAPIService {
  public Response post(String endpoint, String data);

  public Response put(String endpoint, String data);

  public Response get(String endpoint);
}
