package dk.s4_g1.core;

import dk.s4_g1.common.enums.*;
import dk.s4_g1.common.services.*;
import dk.s4_g1.common.util.*;

import org.apache.logging.log4j.*;

import java.util.Scanner;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        var oHttpServer = ServiceLoader.getDefault(IHttpServerService.class);
        if (oHttpServer.isPresent()) {
            oHttpServer.get().start();
        } else {
            logger.info("Could not start IHttpServerService - HttpServerManager");
        }

        startSubscriptionsAndCallback();

        var client = ServiceLoader.getDefault(ICommandService.class).get();
        // set batch
        client.sendCmdInt(Nodes.NEXT_MACHINE_COMMAND, Commands.RESET.id);
        client.sendCmdBool(Nodes.EXECUTE_MACHINE_CMD, true);

        logger.info("Pres any key for exit");
        new Scanner(System.in).next();
        System.exit(0);
    }

    private static void startSubscriptionsAndCallback() {
        var oCallback = ServiceLoader.getDefault(ICallbackSubscription.class);
        var oSubmanager = ServiceLoader.getDefault(ISubscriptionService.class);

        if (oCallback.isPresent() && oSubmanager.isPresent()) {
            var callback = oCallback.get();
            var submanager = oSubmanager.get();

            submanager.addCallback(callback);

            submanager.subscribe(Nodes.BATCH_ID);
            submanager.subscribe(Nodes.PRODUCED_PRODUCTS);
            submanager.subscribe(Nodes.STATUS_MAINTENANCE);
            submanager.subscribe(Nodes.STATUS_BARLEY);
            submanager.subscribe(Nodes.STATUS_MALT);
            submanager.subscribe(Nodes.STATUS_WHEAT);
            submanager.subscribe(Nodes.STATUS_YEAST);
            submanager.subscribe(Nodes.STATUS_HOPS);
            submanager.subscribe(Nodes.STATE);
        } else {
            logger.error("Could not start subscription service and callback");
        }
    }
}
