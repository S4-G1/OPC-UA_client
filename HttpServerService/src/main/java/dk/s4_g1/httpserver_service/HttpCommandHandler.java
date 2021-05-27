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

public class HttpCommandHandler implements HttpHandler {
    private ICommandService client;
    private static Logger logger = LogManager.getLogger(HttpServerManager.class);

    public HttpCommandHandler(ICommandService commandService) {
        this.client = commandService;
    }

    @Override
    public void handle(HttpExchange exchange) {

        var json = getJsonObjectFromExchange(exchange);
        logger.info("Got new Command request: {}", json.toString());

        var command = Commands.getCommand(json.getString("method")).get();

        logger.info("Got command: {}", command);

        client.sendCmdInt(Nodes.NEXT_MACHINE_COMMAND, command.id);
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
