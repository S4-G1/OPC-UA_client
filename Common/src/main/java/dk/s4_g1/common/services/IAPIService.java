package dk.s4_g1.common.services;

import dk.s4_g1.common.data.StatusCode;

public interface IAPIService{
    public StatusCode post(String endpoint, String data);
    public StatusCode put(String endpoint, String data);
    public StatusCode get(String endpoint, String data);
}
