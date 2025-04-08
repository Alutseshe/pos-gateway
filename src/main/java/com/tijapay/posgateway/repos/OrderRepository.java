package com.tijapay.posgateway.repos;

import com.tijapay.posgateway.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findDistinctByOrderNumber(String orderNumber);
    OrderEntity findDistinctByMerchantRequestID(String merchantRequestId);

    @Query(value = "SELECT * FROM api_orders s WHERE s.payment_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<OrderEntity> findByPaymentDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
