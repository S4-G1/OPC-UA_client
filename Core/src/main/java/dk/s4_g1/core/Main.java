package dk.s4_g1.core;

import dk.s4_g1.common.enums.*;
import dk.s4_g1.common.services.*;
import dk.s4_g1.common.util.*;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Server started.");

        var client = ServiceLoader.getDefault(ICommandService.class).get();
        // set batch
        client.sendCmdFloat(Nodes.NEXT_BATCH_ID, 14);
        client.sendCmdFloat(Nodes.NEXT_RECIPE_TYPE, Recipes.WHEAT.ordinal());
        client.sendCmdFloat(Nodes.NEXT_PRODUCT_AMOUNT, 300);
        client.sendCmdFloat(Nodes.NEXT_MACHINE_SPEED, Recipes.WHEAT.speedLimit / 2);

        // start
        client.sendCmdInt(Nodes.NEXT_MACHINE_COMMAND, 2);
        client.sendCmdBool(Nodes.EXECUTE_MACHINE_CMD, true);

        ISubscriptionService submanager =
                ServiceLoader.getDefault(ISubscriptionService.class).get();
        ICallbackSubscription callback =
                ServiceLoader.getDefault(ICallbackSubscription.class).get();

        submanager.subscribe(Nodes.PRODUCED_PRODUCTS, callback);
        submanager.subscribe(Nodes.STATUS_MAINTENANCE, callback);
        submanager.subscribe(Nodes.STATUS_BARLEY, callback);
        submanager.subscribe(Nodes.STATUS_MALT, callback);
        submanager.subscribe(Nodes.STATUS_WHEAT, callback);
        submanager.subscribe(Nodes.STATUS_YEAST, callback);
        submanager.subscribe(Nodes.STATUS_HOPS, callback);

        new Scanner(System.in).next();
        System.out.println("------------------------- Removing MAINTENACE, BARLY, MALT!");

        submanager.stopSubscription(Nodes.STATUS_MAINTENANCE);
        submanager.stopSubscription(Nodes.STATUS_BARLEY);
        submanager.stopSubscription(Nodes.STATUS_MALT);

        System.out.println("------------------------- Pres any ket for all subs!");
        new Scanner(System.in).next();

        submanager.stopAllSubscription();

        System.out.println("Pres any key for exit");
        new Scanner(System.in).next();
        System.out.println("Exit");

        System.exit(0);
    }
}
