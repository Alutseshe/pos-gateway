package com.tijapay.posgateway.services;

import com.tijapay.posgateway.model.OrderRequest;
import com.tijapay.posgateway.utils.ApplicationProperties;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class WhatsAppService {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public WhatsAppService(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    public void sendWhatsAppNotification(OrderRequest request) {
        Twilio.init(applicationProperties.getTwilioAccountSid(), applicationProperties.getTwilioAuthToken());

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(applicationProperties.getTwilioChefWhatsappNumber()),
                        new com.twilio.type.PhoneNumber(applicationProperties.getTwilioWhatsappNumber()),
                "New Order:\n"
                        + "Order Number: " + request.getOderNumber() + "\n"
                        + "Table Number: " + request.getTableNumber() + "\n"
                        + "Amount: " + request.getAmount() + "\n"
                        + "Order Items: " + request.getOrderItems() + "\n"
                        + "Payment: Paid" + "\n"
                        + "Payment Ref: " + "TXGHFY787HFH")
                .create();

        System.out.println("Message sent: " + message);
    }
}
