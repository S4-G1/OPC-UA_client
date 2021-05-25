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
        switch (node){
            //Inventory
            case STATUS_BARLEY:
            case STATUS_MALT:
            case STATUS_WHEAT:
            case STATUS_YEAST:
            case STATUS_HOPS:
                //call method
                break;
                //Maintenance
            case STATUS_MAINTENANCE:
                //Call method
                String s = String.format("{ \"value\": %s }", msg);
                apiUpdate("maintenance/", s);
                break;
            //Data
            case STATUS_HUMIDITY:
            case STATUS_TEMPERATURE:
            case STATUS_VIBRATION:
            case PRODUCED_PRODUCTS:
            case DEFECTIVE_PRODUCTS:
            case STATE:
            //Call method
                break;
            default:
                logger.error("This node does not exist {}: {}", node, msg);
        }
    }


    private void apiUpdate(String endpoint, String msg){
        apiService.put(endpoint, msg);
    }
}