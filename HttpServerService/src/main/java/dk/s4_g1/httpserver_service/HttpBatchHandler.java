package dk.s4_g1.httpserver_service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpBatchHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader buffReader = new BufferedReader(reader);
        String s = buffReader.readLine();

        JSONObject json = new JSONObject(s);
        int id = Integer.parseInt(json.getString("batch_id"));
    }
}
