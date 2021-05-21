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
            default:
                logger.error("This node does not exist {}: {}", node, msg);
        }
    }
}
