package com.tijapay.posgateway.services;

import com.tijapay.posgateway.entities.OrderEntity;
import com.tijapay.posgateway.repos.OrderRepository;
import com.tijapay.posgateway.utils.ApplicationProperties;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class WhatsAppService {

    private final ApplicationProperties applicationProperties;
    private final OrderRepository orderRepository;

    @Autowired
    public WhatsAppService(ApplicationProperties applicationProperties, OrderRepository orderRepository){
        this.applicationProperties = applicationProperties;
        this.orderRepository = orderRepository;
    }

    public void sendWhatsAppNotification(OrderEntity request) {
        Twilio.init(applicationProperties.getTwilioAccountSid(), applicationProperties.getTwilioAuthToken());

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(applicationProperties.getTwilioChefWhatsappNumber()),
                new com.twilio.type.PhoneNumber(applicationProperties.getTwilioWhatsappNumber()),
                "_*New Order:*_\n" +
                        "```\n" +
                        String.format("%-15s: %s%n", "Order Number", request.getOrderNumber()) +
                        String.format("%-15s: %s%n", "Table Number", request.getTableNumber()) +
                        String.format("%-15s: %s%n", "Customer Name", request.getCustomerName()) +
                        String.format("%-15s: %s%n", "Amount", request.getAmount()) +
                        String.format("%-15s: %s%n", "Order Items", request.getOrderItems()) +
                        String.format("%-15s: %s%n", "Payment Status", request.getStatusMessage()) +
                        String.format("%-15s: %s%n", "Paid Via", request.getPaymentMethod()) +
                        String.format("%-15s: %s%n", "Payment Ref", request.getMpesaReceiptNumber()) +
                        "```"
        ).create();

        //TODO::UPDATE STATUS AFTER SENDING

        orderRepository.updateWhatsAppStatus(request.getOrderNumber());

        System.out.println("Message sent: " + message);
    }
}
