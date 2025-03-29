package com.tijapay.posgateway.services.impl;

import com.tijapay.posgateway.model.sms.SmsRequest;
import com.tijapay.posgateway.model.sms.SmsResponse;
import com.tijapay.posgateway.services.SmsService;
import com.tijapay.posgateway.utils.ApplicationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.logging.Logger;

@Service
public class SmsServiceImpl implements SmsService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final ApplicationProperties applicationProperties;

    public SmsServiceImpl(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    @Override
    public SmsResponse sendSms(SmsRequest smsRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("apiKey", "MyApiKey");
        HttpEntity<SmsRequest> httpEntity = new HttpEntity<>(smsRequest, headers);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SmsResponse> responseEntity = restTemplate.exchange(applicationProperties.getMpesaStkEndpoint(),
                    HttpMethod.POST,
                    httpEntity,
                    SmsResponse.class);
            return responseEntity.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e){
            logger.info("Error: " + e.getStatusCode() + " - " + e.getStatusText());
            logger.info("Response Body: " + e.getResponseBodyAsString());
            return null;
        }
    }
}
