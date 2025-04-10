package com.tijapay.posgateway.controllers;

import com.google.gson.Gson;
import com.tijapay.posgateway.entities.OrderEntity;
import com.tijapay.posgateway.model.OrderReponse;
import com.tijapay.posgateway.model.OrderRequest;
import com.tijapay.posgateway.model.ReportModel;
import com.tijapay.posgateway.model.mpesa.MpesaCallback;
import com.tijapay.posgateway.repos.OrderRepository;
import com.tijapay.posgateway.services.Order;
import com.tijapay.posgateway.services.Orderservice;
import com.tijapay.posgateway.services.WhatsAppService;
import com.tijapay.posgateway.utils.ConvertToJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/gateway/")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private final Orderservice orderservice;
    private final Gson gson;
    private final OrderRepository orderRepository;
    private final Order order;
    private final WhatsAppService whatsAppService;


    public ApiController(Orderservice orderservice, Gson gson, OrderRepository orderRepository, Order order, WhatsAppService whatsAppService){
        this.orderservice = orderservice;
        this.gson = gson;
        this.orderRepository = orderRepository;
        this.order = order;
        this.whatsAppService = whatsAppService;
    }

    @PostMapping(value = "payment")
    public OrderReponse payment(@RequestBody OrderRequest request){
//        whatsAppService.sendWhatsAppNotification(request);
        return orderservice.payementRequest(request);
    }

    @GetMapping(value = "getPayments")
    public List<OrderEntity> getPayments(){
        return orderRepository.findAll();
    }

    @PostMapping(value = "getByDate")
    public List<OrderEntity> getPayments(@RequestBody ReportModel dateRangeRequest) {
        return order.getOrdersByDateRange(dateRangeRequest);
    }

    @PostMapping(value = "/mpesa/callback", produces = {"application/json"})
    public ResponseEntity<?> stkPushCallback(@RequestBody MpesaCallback request, OrderEntity entity) {

        Map validateRes = new HashMap();
        try {
            System.out.println("MPESA Callback Received");
            System.out.println(ConvertToJson.setJsonString(request));

            if (request.getBody().getStkCallback().getResultCode() == (0)){
                log.info("--------- Mpesa Payment was Successful, Debit Completed ------- ");
                orderRepository.findDistinctByMerchantRequestID(request.getBody().getStkCallback().getMerchantRequestId());
                log.info(" --------- " + request.getBody().getStkCallback());
                if(orderRepository.findDistinctByMerchantRequestID(request.getBody().getStkCallback().getMerchantRequestId()) != null){
                    OrderEntity updateMpesaReference = orderRepository.findDistinctByMerchantRequestID(request.getBody().getStkCallback().getMerchantRequestId());
                    updateMpesaReference.setMpesaReceiptNumber(request.getBody().getStkCallback().getCallbackMetadata().getItem().get(1).getValue());
                    updateMpesaReference.setStatus("00");
                    updateMpesaReference.setStatusMessage("Paid");
                    if (request.getBody().getStkCallback().getCallbackMetadata().getItem().get(2).getValue() == null) {
                        updateMpesaReference.setTransactionDate(request.getBody().getStkCallback().getCallbackMetadata().getItem().get(3).getValue());
                    } else {
                        updateMpesaReference.setTransactionDate(request.getBody().getStkCallback().getCallbackMetadata().getItem().get(2).getValue());
                    }
                    System.out.println(new Gson().toJson(updateMpesaReference));
                    orderRepository.save(updateMpesaReference);
                }
            }else if(request.getBody().getStkCallback().getResultCode() == (2001)){
                log.info("--------- Failed Wrong pin entered, ------- "+ request.getBody().getStkCallback().getResultCode());
                log.info(" --------- " + request.getBody().getStkCallback().getResultDesc());
//                mpesaRepo.updateFailedDebitRequest(request.getBody().getStkCallback().getMerchantRequestId());
            }else{
                log.info("--------- General Error, ------- "+ request.getBody().getStkCallback().getResultCode());
                log.info(" --------- " + request.getBody().getStkCallback().getResultDesc());
//                mpesaRepo.updateFailedDebitRequest(request.getBody().getStkCallback().getMerchantRequestId());
            }
            validateRes.put("ResultCode", 0);
            validateRes.put("ResultDesc", "Success");
            return new ResponseEntity<>(validateRes, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            validateRes.put("ResultCode", 1);
            validateRes.put("ResultDesc", "System Error");
            return new ResponseEntity<>(validateRes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
