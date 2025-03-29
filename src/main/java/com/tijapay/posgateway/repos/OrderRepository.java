package com.tijapay.posgateway.repos;

import com.tijapay.posgateway.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findDistinctByOrderNumber(String orderNumber);
    OrderEntity findDistinctByMerchantRequestID(String merchantRequestId);

}
