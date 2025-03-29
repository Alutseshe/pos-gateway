package com.tijapay.posgateway.services.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tijapay.posgateway.entities.OrderEntity;
import com.tijapay.posgateway.model.mpesa.MpesaRequestModel;
import com.tijapay.posgateway.model.mpesa.MpesaResponseModel;
import com.tijapay.posgateway.repos.OrderRepository;
import com.tijapay.posgateway.services.MpesaService;
import com.tijapay.posgateway.services.TokenGenerateService;
import com.tijapay.posgateway.utils.ApplicationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class MpesaServiceImpl implements MpesaService {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final ApplicationProperties applicationProperties;
    private final TokenGenerateService tokenGenerateService;
    private final Gson gson;
    private final OrderRepository orderRepository;

    public MpesaServiceImpl(ApplicationProperties applicationProperties,
                            TokenGenerateService tokenGenerateService, Gson gson, OrderRepository orderRepository){
        this.applicationProperties = applicationProperties;
        this.tokenGenerateService = tokenGenerateService;
        this.gson = gson;
        this.orderRepository = orderRepository;
    }

    @Override
    public MpesaResponseModel stkPushRequest(MpesaRequestModel mpesaRequestModel){

        String AccessToken = null;
        AccessToken = String.valueOf(tokenGenerateService.getToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+ AccessToken);
        HttpEntity<MpesaRequestModel> httpEntity = new HttpEntity<>(mpesaRequestModel,headers);

        logger.info("OUGOING MPESA REQUEST:::::" + gson.toJson(mpesaRequestModel));

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    applicationProperties.getMpesaStkEndpoint(),
                    HttpMethod.POST,
                    httpEntity,
                    String.class);

            logger.info("| Incoming STK Push Response | ->" + responseEntity.getBody());

            MpesaResponseModel mpesaResponseModel = gson.fromJson(responseEntity.getBody(), MpesaResponseModel.class);
            String formattedJson = new GsonBuilder().setPrettyPrinting().create().toJson(mpesaResponseModel);
            MpesaResponseModel response = gson.fromJson(formattedJson, MpesaResponseModel.class);

            if (response.getResponseCode().equals("0")) {
                OrderEntity orderEntity = orderRepository.findDistinctByOrderNumber(mpesaRequestModel.getAccountReference());
                orderEntity.setMerchantRequestID(response.getMerchantRequestID());
                orderEntity.setCheckoutRequestID(response.getCheckoutRequestID());
                orderEntity.setResponseCode(response.getResponseCode());
                orderEntity.setResponseDescription(response.getResponseDescription());
                orderEntity.setCustomerMessage(response.getCustomerMessage());
                orderRepository.save(orderEntity);
                return response;
            } else {
                return null;
            }

        } catch (HttpClientErrorException | HttpServerErrorException e){
            logger.info("Error "+ e.getStatusCode() + " - " + e.getStatusText());
            logger.info("Response Body "+ e.getResponseBodyAsString());
            return null;
        }
    }
}
