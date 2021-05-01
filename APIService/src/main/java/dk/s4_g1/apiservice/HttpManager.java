package dk.s4_g1.apiservice;

import dk.s4_g1.common.data.Response;
import dk.s4_g1.common.services.IAPIService;
import kong.unirest.Unirest;
import kong.unirest.UnirestConfigException;
import java.util.logging.Logger;

public class HttpManager implements IAPIService {

    private static final String URL = "https://api.bierproductie.nymann.dev";
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String FORMAT = "%s/%s";

    public HttpManager(){
        try {
            Unirest.config()
                    .setDefaultHeader("Accept", "application/json")
                    .setDefaultHeader("Content-Type", "application/json")
                    .followRedirects(true)
                    .enableCookieManagement(false);
        } catch (UnirestConfigException e) {
            LOGGER.warning("Unirest is already configured, skipping");
        }
    }

    @Override
    public Response post(String endpoint, String data) {
        var response = Unirest.post(String.format(FORMAT, URL, endpoint)).body(data);

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody());
    }

    @Override
    public Response put(String endpoint, String data) {
        var response = Unirest.put(String.format(FORMAT, URL, endpoint)).body(data);

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody());
    }

    @Override
    public Response get(String endpoint) {
        var response = Unirest.get(String.format(FORMAT, URL, endpoint));

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody());
    }
}
