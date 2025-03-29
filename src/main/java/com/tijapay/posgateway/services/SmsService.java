package com.tijapay.posgateway.services;

import com.tijapay.posgateway.model.sms.SmsRequest;
import com.tijapay.posgateway.model.sms.SmsResponse;

public interface SmsService {
    SmsResponse sendSms(SmsRequest smsRequest);
}
