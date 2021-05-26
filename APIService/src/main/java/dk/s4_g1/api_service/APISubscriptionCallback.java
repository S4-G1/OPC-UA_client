package dk.s4_g1.api_service;

import dk.s4_g1.common.enums.*;
import dk.s4_g1.common.services.IAPIService;
import dk.s4_g1.common.services.ICallbackSubscription;
import dk.s4_g1.common.util.*;

import org.apache.logging.log4j.*;

public class APISubscriptionCallback implements ICallbackSubscription {

    IAPIService apiService;
    private static Logger logger = LogManager.getLogger(APISubscriptionCallback.class);

    private DataOverTime curBatch;

    public APISubscriptionCallback() {
        curBatch = new DataOverTime(0);
        var oAPIService = ServiceLoader.getDefault(IAPIService.class);

        if (oAPIService.isPresent()) {
            apiService = oAPIService.get();
        } else {
            logger.warn("Can't setup APIService for ICallbackSubscription");
        }
        logger.info("ICallbackSubscription - APISubscriptionCallback Started");
    }

    @Override
    public void sendMsg(Nodes node, String msg, String timestamp) {
        logger.debug("Node: {}, msg: {}, timestap: {}", node, msg, timestamp);
        String endpoint = "";

        switch (node) {
                // Inventory
            case STATUS_BARLEY:
            case STATUS_MALT:
            case STATUS_WHEAT:
            case STATUS_YEAST:
            case STATUS_HOPS:
                endpoint = getInventoryEndpoint(node);
                apiPut(endpoint, String.format("{ \"current_value\": %s }", msg));
                break;
                // Maintenance
            case STATUS_MAINTENANCE:
                endpoint = "maintenance/";
                apiPut(endpoint, String.format("{ \"value\": %s }", msg));
                break;
                // Data
            case BATCH_ID:
                logger.warn(
                        "Create new DataOverTime with batch id: {}", (int) Float.parseFloat(msg));
                curBatch = new DataOverTime((int) Float.parseFloat(msg));
                break;
            case STATUS_HUMIDITY:
                curBatch.setHumidity(Float.parseFloat(msg));
                batchDataUpdate(timestamp);
                break;
            case STATUS_TEMPERATURE:
                curBatch.setTemperature(Float.parseFloat(msg));
                batchDataUpdate(timestamp);
                break;
            case STATUS_VIBRATION:
                curBatch.setVibration(Float.parseFloat(msg));
                batchDataUpdate(timestamp);
                break;
            case PRODUCED_PRODUCTS:
                curBatch.setProduced(Float.parseFloat(msg));
                batchDataUpdate(timestamp);
                break;
            case DEFECTIVE_PRODUCTS:
                curBatch.setRejected(Integer.parseInt(msg));
                batchDataUpdate(timestamp);
                break;
            case STATE:
                logger.info(
                        "Changed state to: {}",
                        MachineState.getStateFromValue(Integer.parseInt(msg)).get());
                curBatch.setState(Integer.parseInt(msg));
                batchDataUpdate(timestamp);
                break;
            default:
                logger.error("This node does not exist {}: {}", node, msg);
                return;
        }
    }

    private String getInventoryEndpoint(Nodes node) {
        String endpoint = "inventory_statuses/%s";

        switch (node) {
            case STATUS_BARLEY:
                return String.format(endpoint, "Barley");
            case STATUS_HOPS:
                return String.format(endpoint, "Hops");
            case STATUS_MALT:
                return String.format(endpoint, "Malt");
            case STATUS_WHEAT:
                return String.format(endpoint, "Wheat");
            case STATUS_YEAST:
                return String.format(endpoint, "Yeast");
            default:
                logger.error("Could not get the right inventory_status endpoint!");
        }

        return endpoint;
    }

    private void batchDataUpdate(String timestamp) {
        String endpoint = "data_over_time/";
        if (curBatch.getBatchId() == 0) {
            logger.warn(
                    "Batch id: {} it should not be zero else it can not post",
                    curBatch.getBatchId());
            return;
        }

        if (curBatch.getMsTime().equals(timestamp)) {
            logger.debug("apiService PUT Batch: {}", curBatch.toJson());
            apiService.put(endpoint, curBatch.toJson());
        } else {
            curBatch.setMsTime(timestamp);
            logger.debug("apiService POST Batch: {}", curBatch.toJson());
            apiService.post(endpoint, curBatch.toJson());
        }
    }

    private void apiPut(String endpoint, String msg) {
        apiService.put(endpoint, msg);
    }
}
