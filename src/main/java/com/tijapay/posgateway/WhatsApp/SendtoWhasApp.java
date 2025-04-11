package com.tijapay.posgateway.WhatsApp;

import com.tijapay.posgateway.entities.OrderEntity;
import com.tijapay.posgateway.repos.OrderRepository;
import com.tijapay.posgateway.services.WhatsAppService;
import com.tijapay.posgateway.utils.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SendtoWhasApp {
    private Logger logger = LoggerFactory.getLogger(SendtoWhasApp.class);
    private final OrderRepository orderRepository;
    private final WhatsAppService whatsAppService;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public SendtoWhasApp(OrderRepository orderRepository, WhatsAppService whatsAppService, ApplicationProperties applicationProperties){
        this.orderRepository = orderRepository;
        this.whatsAppService = whatsAppService;
        this.applicationProperties = applicationProperties;
    }

    @Scheduled(fixedRateString = "${configs.scheduler-fixed-rate-query-status}",
            initialDelayString = "${configs.scheduler-initial-delay-query-status}")


    public void scheduledTask() throws InterruptedException{
        List<OrderEntity> lst = new ArrayList<OrderEntity>();
        lst = stagingList();
        if(lst.size()> 0) {
            for(int i=0; i<lst.size(); i++) {
                logger.info("*********** INDEX :: "+ String.valueOf(i) +
                        " *********** END-TO-END-ID :: "+ lst.get(i).getOrderNumber());
                queryStatus(lst.get(i));
            }
        }else {
            logger.info("******** ALL ORDER REQUESTS SENT ******** ");
        }
    }

    private void queryStatus(OrderEntity sendWhasApp) {
        new Thread(new Runnable() {
            public void run() {
                whatsAppService.sendWhatsAppNotification(sendWhasApp);
            }
        }).start();
    }

    private List<OrderEntity> stagingList(){
        List<OrderEntity> lst = new ArrayList<OrderEntity>();
        try {
            lst = orderRepository.findMissingWhatsAppCall(applicationProperties.getBatchSize());
            return lst;
        }catch(Exception e) {
            logger.error(e.getMessage());
        }
        return lst;
    }


}
