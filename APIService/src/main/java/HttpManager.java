import dk.s4_g1.common.data.Response;
import dk.s4_g1.common.services.IAPIService;
import kong.unirest.Unirest;
import kong.unirest.UnirestConfigException;
import java.util.logging.Logger;

public class HttpManager implements IAPIService {

    private static final String URL = "https://api.bierproductie.nymann.dev";
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
        LOGGER.info(String.format("Post %s to %s", data, endpoint));
        var response = Unirest.post(String.format("%s/%s", URL, endpoint)).body(data);

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody().toString());
    }

    @Override
    public Response put(String endpoint, String data) {
        LOGGER.info(String.format("Put %s to %s", data, endpoint));
        var response = Unirest.put(String.format("%s/%s", URL, endpoint)).body(data);

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody().toString());
    }

    @Override
    public Response get(String endpoint) {
        LOGGER.info(String.format("Get from %s", endpoint));
        var response = Unirest.get(String.format("%s/%s", URL, endpoint));

        var responseString = response.asString();
        return new Response(responseString.getStatus(), responseString.getBody().toString());
    }
}
