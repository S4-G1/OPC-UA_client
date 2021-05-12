package dk.s4_g1.apiservice;

import dk.s4_g1.common.data.Response;
import dk.s4_g1.common.services.IAPIService;
import kong.unirest.Unirest;
import kong.unirest.UnirestConfigException;
import org.apache.logging.log4j.*;

public class HttpManager implements IAPIService {

    private static final String URL = "https://api.bierproductie.nymann.dev";
    private static Logger logger = LogManager.getLogger(HttpManager.class);
    private static final Marker API = MarkerManager.getMarker("API");
    private static final String FORMAT = "%s/%s";

    public HttpManager(){
        try {
            Unirest.config()
                    .setDefaultHeader("Accept", "application/json")
                    .setDefaultHeader("Content-Type", "application/json")
                    .followRedirects(true)
                    .enableCookieManagement(false);
        } catch (UnirestConfigException e) {
            logger.warn(API, "Unirest is already configured, skipping");
        }
    }

    @Override
    public Response post(String endpoint, String data) {
        var response = Unirest.post(endpointFormat(endpoint)).body(data);

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody());
    }

    @Override
    public Response put(String endpoint, String data) {
        var response = Unirest.put(endpointFormat(endpoint)).body(data);

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody());
    }

    @Override
    public Response get(String endpoint) {
        var response = Unirest.get(endpointFormat(endpoint));

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody());
    }

    public String endpointFormat(String endpoint){
        return String.format(FORMAT, URL, endpoint);
    }
}
