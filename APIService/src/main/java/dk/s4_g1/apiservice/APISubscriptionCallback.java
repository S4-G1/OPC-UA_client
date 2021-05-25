package dk.s4_g1.apiservice;

import org.apache.logging.log4j.*;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.services.ICallbackSubscription;

import dk.s4_g1.common.services.IAPIService;
import dk.s4_g1.common.util.*;

public class APISubscriptionCallback implements ICallbackSubscription{

    IAPIService apiService;
    private static Logger logger = LogManager.getLogger(APISubscriptionCallback.class);

    public APISubscriptionCallback(){
        var oAPIService = ServiceLoader.getDefault(IAPIService.class);

        if(oAPIService.isPresent()){
            apiService = oAPIService.get();
        }
    }

    @Override
    public void sendMsg(Nodes node, String msg) {
        String endpoint = "";
        String s = "";
        node.name();

        switch (node){
            //Inventory
            case STATUS_BARLEY:
            case STATUS_MALT:
            case STATUS_WHEAT:
            case STATUS_YEAST:
            case STATUS_HOPS:
                endpoint = getInventoryEndpoint(node);
                s = String.format("{ \"value\": %s }", msg);
                break;
            //Maintenance
            case STATUS_MAINTENANCE:
                endpoint = "maintenance/";
                s = String.format("{ \"value\": %s }", msg);
                break;
            //Data
            case STATUS_HUMIDITY:
            case STATUS_TEMPERATURE:
            case STATUS_VIBRATION:
            case PRODUCED_PRODUCTS:
            case DEFECTIVE_PRODUCTS:
            case STATE:
                //Call method
                endpoint = getBatchEndpoint(node);
                break;
            default:
                logger.error("This node does not exist {}: {}", node, msg);
                return;
        }

        apiUpdate(endpoint, s);
    }


    private String getInventoryEndpoint(Nodes node){
        String endpoint = "";

        switch (node){
            case STATUS_BARLEY:
                endpoint = "Barley";
                break;
            case STATUS_HOPS:
                endpoint = "Hops";
                break;
            case STATUS_MALT:
                endpoint = "Malt";
                break;
            case STATUS_WHEAT:
                endpoint = "Wheat";
                break;
            case STATUS_YEAST:
                endpoint = "Yeast";
                break;
            default:
            logger.error("Something went wrong");
        }

        return endpoint;
    }

    private String getBatchEndpoint(Nodes node){
        String endpoint = "";
        switch (node){
            case STATUS_HUMIDITY:
            case STATUS_TEMPERATURE:
            case STATUS_VIBRATION:
            case PRODUCED_PRODUCTS:
            case DEFECTIVE_PRODUCTS:
            case STATE:
        }
        return endpoint;
    }


    private void apiUpdate(String endpoint, String msg){
        apiService.put(endpoint, msg);
    }
}