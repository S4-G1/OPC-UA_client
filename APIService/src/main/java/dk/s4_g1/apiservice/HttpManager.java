package dk.s4_g1.apiservice;

import dk.s4_g1.common.data.Response;
import dk.s4_g1.common.services.*;

import kong.unirest.Unirest;
import kong.unirest.UnirestConfigException;

import org.apache.logging.log4j.*;

public class HttpManager implements IAPIService {

    private static final String FORMAT = "%s/%s";
    private static Logger logger = LogManager.getLogger(HttpManager.class);
    private static IConfigService configLoader;
    private static String url;

    public HttpManager() {
        try {
            Unirest.config()
                    .setDefaultHeader("Accept", "application/json")
                    .setDefaultHeader("Content-Type", "application/json")
                    .followRedirects(true)
                    .enableCookieManagement(false);
        } catch (UnirestConfigException e) {
            logger.warn("Unirest is already configured, skipping");
        }

        configLoader = java.util.ServiceLoader.load(IConfigService.class).findFirst().get();
        url = configLoader.getConfig("API_URL").get();

        logger.info("IAPIService - HttpManger Created");
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

    protected String endpointFormat(String endpoint) {
        return String.format(FORMAT, url, endpoint);
    }
}
