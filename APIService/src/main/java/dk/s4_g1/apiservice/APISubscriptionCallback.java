package dk.s4_g1.apiservice;

import org.apache.logging.log4j.*;

import dk.s4_g1.common.enums.Nodes;
import dk.s4_g1.common.services.ICallbackSubscription;

import dk.s4_g1.common.services.IAPIService;
import dk.s4_g1.common.util.*;

public class APISubscriptionCallback implements ICallbackSubscription{

    IAPIService apiService;
    private static Logger logger = LogManager.getLogger(APISubscriptionCallback.class);

    private DataOverTime curBatch;

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
                apiPut(endpoint, msg);
                break;
            //Maintenance
            case STATUS_MAINTENANCE:
                endpoint = "maintenance/";
                s = String.format("{ \"value\": %s }", msg);
                apiPut(endpoint, msg);
                break;
            //Data
            case STATUS_HUMIDITY:
                endpoint = "data_over_time";
                curBatch.setHumidity(Float.parseFloat(msg));
                s = curBatch.toJson();
                apiPost(endpoint, msg);
                break;
            case STATUS_TEMPERATURE:
                endpoint = "data_over_time";
                curBatch.setTemperature(Float.parseFloat(msg));
                apiPost(endpoint, msg);
                break;
            case STATUS_VIBRATION:
                endpoint = "data_over_time";
                curBatch.setVibration(Float.parseFloat(msg));
                apiPost(endpoint, msg);
                break;
            case PRODUCED_PRODUCTS:
                endpoint = "data_over_time";
                curBatch.setProduced(Float.parseFloat(msg));
                apiPost(endpoint, msg);
                break;
            case DEFECTIVE_PRODUCTS:
                endpoint = "data_over_time";
                curBatch.setRejected(Integer.parseInt(msg));
                apiPost(endpoint, msg);
                break;
            case STATE:
                endpoint = "data_over_time";
                curBatch.setState(Integer.parseInt(msg));
                apiPost(endpoint, msg);
                break;
            default:
                logger.error("This node does not exist {}: {}", node, msg);
                return;
        }

        apiPut(endpoint, s);
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


    private void apiPut(String endpoint, String msg){
        apiService.put(endpoint, msg);
    }

    private void apiPost(String endpoint, String msg){
        apiService.post(endpoint, msg);
    }

    @Override
    public void setBatch(int id) {
        curBatch = new DataOverTime(id);
    }
}