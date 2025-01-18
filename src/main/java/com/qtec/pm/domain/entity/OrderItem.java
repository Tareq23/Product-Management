package com.qtec.pm.domain.entity;

import com.qtec.pm.domain.valueobject.OrderItemId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_ITEMS", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class OrderItem {


    @EmbeddedId
    private OrderItemId orderItemId;

    @Column(name = "quantity")
    @Min(1)
    @Max(5)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

}
