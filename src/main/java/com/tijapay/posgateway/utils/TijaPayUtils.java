package com.tijapay.posgateway.utils;

import com.tijapay.posgateway.model.mpesa.MpesaRequestModel;
import com.tijapay.posgateway.model.sms.SmsRequest;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TijaPayUtils {

    public static MpesaRequestModel stkPushRequest(Long shortcode, String Password, String timestamp, String transactionType,
                                                   Long amount, Long partyA, Long partyB, Long phonenumber,
                                                   String callBbackurl, String accountreference, String transactiondesc){
        MpesaRequestModel mpesaRequestModel = new MpesaRequestModel();
        mpesaRequestModel.setBusinessShortCode(shortcode);
        mpesaRequestModel.setPassword(Password);
        mpesaRequestModel.setTimestamp(timestamp);
        mpesaRequestModel.setTransactionType(transactionType);
        mpesaRequestModel.setAmount(amount);
        mpesaRequestModel.setPartyA(partyA);
        mpesaRequestModel.setPartyB(partyB);
        mpesaRequestModel.setPhoneNumber(phonenumber);
        mpesaRequestModel.setCallBackURL(callBbackurl);
        mpesaRequestModel.setAccountReference(accountreference);
        mpesaRequestModel.setTransactionDesc(transactiondesc);
        return mpesaRequestModel;
    }

    public static SmsRequest sendSms(String username, String message, String senderId, List<String> phoneNumbers){

        SmsRequest request = new SmsRequest();
        request.setMessage(message);
        request.setUsername(username);
        request.setSenderId(senderId);
        request.setPhoneNumbers(phoneNumbers);
        return request;
    }
}
