package com.qtec.pm.domain.entity;


import com.qtec.pm.domain.valueobject.OrderStatus;
import com.qtec.pm.domain.valueobject.PaymentMethod;
import com.qtec.pm.domain.valueobject.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

//    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customerId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "shipping_address", nullable = true, length = 200)
    private String shippingAddress;

    @Column(name = "billing_address", nullable = true, length = 200)
    private String billingAddress;


    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", columnDefinition = "varchar(20) default 'PENDING'")
    private OrderStatus status;


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", columnDefinition = "varchar(20) default 'PENDING'")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "varchar(20) default 'PENDING'")
    private PaymentStatus paymentStatus;


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "")
//    private List<OrderItem> orderItems;

}
