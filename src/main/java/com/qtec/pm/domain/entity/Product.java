package com.qtec.pm.domain.entity;


import com.qtec.pm.domain.valueobject.Category;
import com.qtec.pm.domain.valueobject.Discount;
import com.qtec.pm.domain.valueobject.Price;
import com.qtec.pm.domain.valueobject.StockQuantity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String description;

    @Embedded
    private Price price;

    @Embedded
    private StockQuantity stockQuantity;

    @Embedded
    private Category category;

    @Embedded
    private Discount discount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(String name, Category category, String description, Price price, StockQuantity stockQuantity, Discount discount){
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.discount = discount;
    }

    public Product() {

    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }


}
