package dk.s4_g1.opcuaservice;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.enums.Recipes;
import dk.s4_g1.common.services.ICommandService;

import org.eclipse.milo.opcua.stack.core.UaException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        var client = java.util.ServiceLoader.load(ICommandService.class).findFirst().get();
        // set batch
        testOut(client.sendCmdFloat(Nodes.NEXT_BATCH_ID, 14));
        testOut(client.sendCmdFloat(Nodes.NEXT_RECIPE_TYPE, Recipes.WHEAT.ordinal()));
        testOut(client.sendCmdFloat(Nodes.NEXT_PRODUCT_AMOUNT, 300));
        testOut(client.sendCmdFloat(Nodes.NEXT_MACHINE_SPEED, Recipes.WHEAT.speedLimit / 2));

        // start
        testOut(client.sendCmdInt(Nodes.NEXT_MACHINE_COMMAND, 2));
        testOut(client.sendCmdBool(Nodes.EXECUTE_MACHINE_CMD, true));

        SubscribtionManager submanager = null;
        try {
            submanager = new SubscribtionManager();
        } catch (UaException e) {
            System.out.println("Fuck exceptions");
        }
        if (submanager == null) {
            System.exit(0);
        }

        submanager.subscribe(Nodes.STATE);
        submanager.subscribe(Nodes.PRODUCED_PRODUCTS);
        submanager.subscribe(Nodes.STATUS_MAINTENANCE);
        submanager.subscribe(Nodes.STATUS_BARLEY);
        submanager.subscribe(Nodes.STATUS_MALT);
        submanager.subscribe(Nodes.STATUS_WHEAT);
        submanager.subscribe(Nodes.STATUS_YEAST);
        submanager.subscribe(Nodes.STATUS_HOPS);

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

    public static void testOut(boolean bool) {
        if (!bool) {
            System.out.println("FUCK");
        }
    }
}
