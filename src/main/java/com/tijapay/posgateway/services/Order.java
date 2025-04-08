package com.tijapay.posgateway.services;

import com.tijapay.posgateway.entities.OrderEntity;
import com.tijapay.posgateway.model.ReportModel;
import com.tijapay.posgateway.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class Order {

    private final OrderRepository orderRepository;

    @Autowired
    public Order(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public List<OrderEntity> getOrdersByDateRange(ReportModel report) {
        LocalDate startDate = report.getStartDate();
        LocalDate endDate = report.getEndDate();
        return orderRepository.findByPaymentDateBetween(startDate, endDate);
    }

}
