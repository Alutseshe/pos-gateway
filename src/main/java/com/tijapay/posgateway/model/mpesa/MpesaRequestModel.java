package com.tijapay.posgateway.model.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MpesaRequestModel {

    @JsonProperty("BusinessShortCode")
    private Long BusinessShortCode;
    @JsonProperty("Password")
    private String Password;
    @JsonProperty("Timestamp")
    private String Timestamp;
    @JsonProperty("TransactionType")
    private String TransactionType;
    @JsonProperty("Amount")
    private Long Amount;
    @JsonProperty("PartyA")
    private Long PartyA;
    @JsonProperty("PartyB")
    private Long PartyB;
    @JsonProperty("PhoneNumber")
    private Long PhoneNumber;
    @JsonProperty("CallBackURL")
    private String CallBackURL;
    @JsonProperty("AccountReference")
    private String AccountReference;
    @JsonProperty("TransactionDesc")
    private String TransactionDesc;

    public Long getBusinessShortCode() {
        return BusinessShortCode;
    }

    public void setBusinessShortCode(Long businessShortCode) {
        BusinessShortCode = businessShortCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public Long getAmount() {
        return Amount;
    }

    public void setAmount(Long amount) {
        Amount = amount;
    }

    public Long getPartyA() {
        return PartyA;
    }

    public void setPartyA(Long partyA) {
        PartyA = partyA;
    }

    public Long getPartyB() {
        return PartyB;
    }

    public void setPartyB(Long partyB) {
        PartyB = partyB;
    }

    public Long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCallBackURL() {
        return CallBackURL;
    }

    public void setCallBackURL(String callBackURL) {
        CallBackURL = callBackURL;
    }

    public String getAccountReference() {
        return AccountReference;
    }

    public void setAccountReference(String accountReference) {
        AccountReference = accountReference;
    }

    public String getTransactionDesc() {
        return TransactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        TransactionDesc = transactionDesc;
    }
}
