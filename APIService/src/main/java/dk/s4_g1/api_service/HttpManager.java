package dk.s4_g1.api_service;

import dk.s4_g1.common.data.Response;
import dk.s4_g1.common.services.*;
import dk.s4_g1.common.util.*;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestConfigException;

import org.apache.logging.log4j.*;

public class HttpManager implements IAPIService {

    private static final String FORMAT = "%s/%s";
    private static final String LOGWARN =
            "Could not {}: url: {}, data: {}, respone_status: {}, respone_headers: {},"
                    + " response_body: {}";
    private static Logger logger = LogManager.getLogger(HttpManager.class);
    private String url;

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

        this.url = getUrlFromConfig();

        logger.info("IAPIService - HttpManger Created with url {}", url);
    }

    @Override
    public Response post(String endpoint, String data) {
        var response = Unirest.post(endpointFormat(endpoint)).body(data);

        var responseString = response.asString();
        logIfFailedOrDebug("POST", endpoint, data, responseString);

        return new Response(responseString.getStatus(), responseString.getBody());
    }

    @Override
    public Response put(String endpoint, String data) {
        var response = Unirest.put(endpointFormat(endpoint)).body(data);

        var responseString = response.asString();
        logIfFailedOrDebug("PUT", endpoint, data, responseString);

        return new Response(responseString.getStatus(), responseString.getBody());
    }

    @Override
    public Response get(String endpoint) {
        var response = Unirest.get(endpointFormat(endpoint));

        var responseString = response.asString();
        logIfFailedOrDebug("GET", endpoint, "", responseString);

        return new Response(responseString.getStatus(), responseString.getBody());
    }

    protected String endpointFormat(String endpoint) {
        return String.format(FORMAT, url, endpoint);
    }

    private String getUrlFromConfig() {
        var oConfigLoader = ServiceLoader.getDefault(IConfigService.class);
        if (oConfigLoader.isEmpty()) {
            return "https://api.bierproductie.nymann.dev";
        }
        return oConfigLoader
                .get()
                .getConfig("API_URL")
                .orElse("https://api.bierproductie.nymann.dev");
    }

    private void logIfFailedOrDebug(
            String method, String endpoint, String data, HttpResponse<String> responseString) {
        logger.debug(
                "POST: endpoint: {}, data: {}, respone_status: {}",
                endpoint,
                data,
                responseString.getStatus());

        if (responseString.isSuccess()) {
            return;
        }
        logger.warn(
                LOGWARN,
                method,
                endpointFormat(endpoint),
                data,
                responseString.getStatus(),
                responseString.getHeaders(),
                responseString.getBody());
    }
}
