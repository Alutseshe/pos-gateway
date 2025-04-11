package com.tijapay.posgateway.repos;

import com.tijapay.posgateway.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findDistinctByOrderNumber(String orderNumber);
    OrderEntity findDistinctByMerchantRequestID(String merchantRequestId);

    @Query(value = "SELECT * FROM api_orders s WHERE s.payment_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<OrderEntity> findByPaymentDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT * FROM api_orders s WHERE s.status " +
            "= '00' AND s.status_message='Paid' LIMIT :batchSize", nativeQuery = true)
    List<OrderEntity> findMissingWhatsAppCall(@Param("batchSize") int batchSize);

    @Modifying(clearAutomatically =true)
    @Transactional
    @Query(value = "UPDATE api_orders s SET s.status='02' " +
            "WHERE order_number=:orderNumber", nativeQuery = true)
    int updateWhatsAppStatus(
            @Param("orderNumber") String orderNumber
    );
}
