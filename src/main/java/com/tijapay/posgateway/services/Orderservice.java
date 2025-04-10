package com.tijapay.posgateway.services;

import com.google.gson.Gson;
import com.tijapay.posgateway.entities.OrderEntity;
import com.tijapay.posgateway.model.OrderReponse;
import com.tijapay.posgateway.model.OrderRequest;
import com.tijapay.posgateway.repos.OrderRepository;
import com.tijapay.posgateway.utils.ApplicationProperties;
import com.tijapay.posgateway.utils.TijaPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

@Service
public class Orderservice {
    private static final Logger logger = LoggerFactory.getLogger(Orderservice.class);

    private final OrderRepository orderRepository;
    private final Gson gson;
    private final MpesaService mpesaService;
    private final ApplicationProperties applicationProperties;


    @Autowired
    public Orderservice(OrderRepository orderRepository, Gson gson, MpesaService mpesaService,
                        ApplicationProperties applicationProperties){
        this.orderRepository = orderRepository;
        this.gson = gson;
        this.mpesaService = mpesaService;
        this.applicationProperties = applicationProperties;
    }

    public OrderReponse payementRequest(OrderRequest request){

        logger.info("RECEIVED PAYMENT REQUEST:::: "+ gson.toJson(request));

        String lipaTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String orderDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        System.out.println("Local Date" + orderDate);

        OrderEntity findOrder = orderRepository.findDistinctByOrderNumber(request.getOderNumber());

        String combinedString = applicationProperties.getMpesaBusinessShortCode() + applicationProperties.getMpesaPassKey() + lipaTime;
        String lipaNaMpesaPassword = Base64.getEncoder().encodeToString(combinedString.getBytes());

        System.out.println(lipaNaMpesaPassword);

        OrderEntity entity = new OrderEntity();
        entity.setOrderNumber(request.getOderNumber());
        entity.setMsisdn(String.valueOf(request.getMsisdn()));
        entity.setTableNumber(request.getTableNumber());
        entity.setAmount(String.valueOf(request.getAmount()));
        entity.setStatus("01");
        entity.setStatusMessage("New Request");
        entity.setCurrency(request.getCurrency());
        entity.setCountryCode(request.getCountyCode());
        entity.setOrderItems(String.valueOf(request.getOrderItems()));
        entity.setPaymentDate(LocalDate.parse(orderDate));
        entity.setPaymentMethod(request.getPaymentMethod());
        orderRepository.save(entity);

        mpesaService.stkPushRequest(TijaPayUtils.stkPushRequest(applicationProperties.getMpesaBusinessShortCode(),
                lipaNaMpesaPassword,
                lipaTime, "CustomerPayBillOnline",
                request.getAmount(), request.getMsisdn(), applicationProperties.getMpesaBusinessShortCode(),
                request.getMsisdn(), applicationProperties.getMpesaCallBack(), request.getOderNumber(), "Test"));

        OrderReponse reponse = new OrderReponse();
        reponse.setStatusCode("0");
        reponse.setStatusMessage("Order Received Successfully");
        return reponse;
    }


}
