package com.tijapay.posgateway.services;

import com.tijapay.posgateway.model.mpesa.MpesaRequestModel;
import com.tijapay.posgateway.model.mpesa.MpesaResponseModel;

public interface MpesaService {
    MpesaResponseModel stkPushRequest(MpesaRequestModel mpesaRequestModel);
}
