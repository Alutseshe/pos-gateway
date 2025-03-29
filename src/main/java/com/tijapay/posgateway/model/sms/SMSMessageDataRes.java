package com.tijapay.posgateway.model.sms;


import java.util.List;

public class SMSMessageDataRes {

    private String Message;
    private List<Recipients> recipients;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Recipients> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipients> recipients) {
        this.recipients = recipients;
    }
}
