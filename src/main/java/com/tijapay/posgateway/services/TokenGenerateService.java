package com.tijapay.posgateway.services;

import com.google.gson.Gson;
import com.tijapay.posgateway.model.mpesa.TokenResponse;
import com.tijapay.posgateway.utils.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.tijapay.posgateway.utils.CacheConfig.TOKEN_DETAILS_CACHE;

@Service
public class TokenGenerateService {

    private static final Logger log = LoggerFactory.getLogger(TokenGenerateService.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @CacheEvict(value = TOKEN_DETAILS_CACHE,allEntries = true)
    public void cleaCache (){
    }

    public String getToken() {

        Gson gson = new Gson();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", applicationProperties.getMpesaGrantType());
        log.info("Outgoing token Request {} to URL {}", new Gson().toJson(formData), applicationProperties.getMpesaAuthEndpoint());

        String auth = applicationProperties.getMpesaConsumerKey() + ":" + applicationProperties.getMpesaSecretKey();
        byte[] baciAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));

        try {
            String resp = WebClient.create()
                    .get()
                    .uri(applicationProperties.getMpesaAuthEndpoint())
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(new String(baciAuth)))
                    .headers(httpHeaders -> httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)))
                    .exchange()
                    .block()
                    .bodyToMono(String.class)
                    .block();

            System.out.println(resp);

            TokenResponse tokenResponse = gson.fromJson(String.valueOf(resp), TokenResponse.class);
            log.info("---------- Received Token Response ----------" + tokenResponse);
            return tokenResponse.getAccess_token();

        } catch (Exception ex) {
            log.info("--------- Outgoing token Response failure {}", ex.getMessage());
            return null;
        }
    }
}
