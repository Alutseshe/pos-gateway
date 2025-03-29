package com.tijapay.posgateway.model.mpesa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MpesaCallback {

    private final Body body;

    @JsonCreator
    public MpesaCallback(@JsonProperty("Body") Body body) {
        this.body = body;
    }

    @JsonProperty("Body")
    public Body getBody() {
        return body;
    }

    public static class Body {
        private final StkCallback stkCallback;

        @JsonCreator
        public Body(@JsonProperty("stkCallback") StkCallback stkCallback) {
            this.stkCallback = stkCallback;
        }

        @JsonProperty("stkCallback")
        public StkCallback getStkCallback() {
            return stkCallback;
        }

        public static class StkCallback {
            private final String merchantRequestId;

            private final String checkoutRequestId;

            private final int resultCode;

            private final String resultDesc;

            private final CallbackMetadata callbackMetadata;

            @JsonCreator
            public StkCallback(@JsonProperty("MerchantRequestID") String merchantRequestId,
                               @JsonProperty("CheckoutRequestID") String checkoutRequestId,
                               @JsonProperty("ResultCode") int resultCode,
                               @JsonProperty("ResultDesc") String resultDesc,
                               @JsonProperty("CallbackMetadata") CallbackMetadata callbackMetadata) {
                this.merchantRequestId = merchantRequestId;
                this.checkoutRequestId = checkoutRequestId;
                this.resultCode = resultCode;
                this.resultDesc = resultDesc;
                this.callbackMetadata = callbackMetadata;
            }

            @JsonProperty("MerchantRequestID")
            public String getMerchantRequestId() {
                return merchantRequestId;
            }

            @JsonProperty("CheckoutRequestID")
            public String getCheckoutRequestId() {
                return checkoutRequestId;
            }

            @JsonProperty("ResultCode")
            public int getResultCode() {
                return resultCode;
            }

            @JsonProperty("ResultDesc")
            public String getResultDesc() {
                return resultDesc;
            }

            @JsonProperty("CallbackMetadata")
            public CallbackMetadata getCallbackMetadata() {
                return callbackMetadata;
            }

            public static class CallbackMetadata {
                private final List<Item> item;

                @JsonCreator
                public CallbackMetadata(@JsonProperty("Item") List<Item> item) {
                    this.item = item;
                }

                @JsonProperty("Item")
                public List<Item> getItem() {
                    return item;
                }

                public static class Item {
                    private final String name;
                    private final String value;

                    @JsonCreator
                    public Item(@JsonProperty("Name") String name,
                                @JsonProperty("Value") String value) {
                        this.name = name;
                        this.value = value;
                    }

                    @JsonProperty("Name")
                    public String getName() {
                        return name;
                    }

                    @JsonProperty("Value")
                    public String getValue() {
                        return value;
                    }
                }
            }
        }
    }
}
