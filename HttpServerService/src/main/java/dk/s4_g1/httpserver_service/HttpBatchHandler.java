package dk.s4_g1.httpserver_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONObject;

import dk.s4_g1.common.services.ICallbackSubscription;
import dk.s4_g1.common.util.ServiceLoader;

public class HttpBatchHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub
        var oCallbackService = ServiceLoader.getDefault(ICallbackSubscription.class);

        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader buffReader = new BufferedReader(reader);
        String s = buffReader.readLine();

        JSONObject json = new JSONObject(s);
        int id = Integer.parseInt(json.getString("batch_id"));

        if(oCallbackService.isPresent()){
            oCallbackService.get().setBatch(id);
        }
    }
}
