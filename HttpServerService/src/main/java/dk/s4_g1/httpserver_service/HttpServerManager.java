package dk.s4_g1.httpserver_service;

import com.sun.net.httpserver.HttpServer;

import dk.s4_g1.common.services.IHttpServerService;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpServerManager implements IHttpServerService {

    @Override
    public void start() {
        try {
            ThreadPoolExecutor threadPoolExecutor =
                    (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/batch", new HttpBatchHandler());
            server.setExecutor(threadPoolExecutor);
            // HandlerFactory.getInstance(true);
            server.start();
        } catch (Exception e) {
            // Add some logger here
        }
    }
}
