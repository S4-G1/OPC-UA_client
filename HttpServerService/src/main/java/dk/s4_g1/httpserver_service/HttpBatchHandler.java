package dk.s4_g1.httpserver_service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import dk.s4_g1.common.enums.*;
import dk.s4_g1.common.services.ICommandService;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class HttpBatchHandler implements HttpHandler {
    private ICommandService client;
    private static Logger logger = LogManager.getLogger(HttpServerManager.class);

    public HttpBatchHandler(ICommandService commandService) {
        this.client = commandService;
    }

    @Override
    public void handle(HttpExchange exchange) {

        var json = getJsonObjectFromExchange(exchange);
        logger.info("Got new request: {}", json.toString());

        var batchId = json.getInt("batch_id");
        var recipe = Recipes.getProduct(json.getString("recipe")).get();
        var amountToProduce = json.getFloat("amount_to_produce");
        var speed = json.getFloat("speed");
        logger.info(
                "Got a batch. batch_id: {}, recipe: {}, amount_to_produce: {}, speed: {}",
                batchId,
                recipe,
                amountToProduce,
                speed);

        client.sendCmdFloat(Nodes.NEXT_BATCH_ID, batchId);
        client.sendCmdFloat(Nodes.NEXT_RECIPE_TYPE, recipe.ordinal());
        client.sendCmdFloat(Nodes.NEXT_PRODUCT_AMOUNT, amountToProduce);
        client.sendCmdFloat(Nodes.NEXT_MACHINE_SPEED, speed);

        // start
        client.sendCmdInt(Nodes.NEXT_MACHINE_COMMAND, Commands.START.id);
        client.sendCmdBool(Nodes.EXECUTE_MACHINE_CMD, true);
        try {
            exchange.sendResponseHeaders(201, -1);
        } catch (IOException e) {
        }
    }

    private JSONObject getJsonObjectFromExchange(HttpExchange exchange) {
        var jsonString =
                new BufferedReader(
                                new InputStreamReader(
                                        exchange.getRequestBody(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
        return new JSONObject(jsonString);
    }
}
