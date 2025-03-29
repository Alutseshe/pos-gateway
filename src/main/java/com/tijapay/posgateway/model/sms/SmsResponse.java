package com.tijapay.posgateway.model.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsResponse {

    @JsonProperty("SMSMessageData")
    private SMSMessageDataRes SMSMessageData;

    public SMSMessageDataRes getSMSMessageData() {
        return SMSMessageData;
    }

    public void setSMSMessageData(SMSMessageDataRes SMSMessageData) {
        this.SMSMessageData = SMSMessageData;
    }
}
