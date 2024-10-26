package com.qtec.pm.domain.valueobject;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class StockQuantity {

    @Column(name="stock_quantity")
    private Integer quantity;

    public StockQuantity(Integer stockQuantity){
        if(stockQuantity == null){
            stockQuantity = 0;
        }
        if(stockQuantity < 0){
            throw new IllegalArgumentException("Stock Quantity must be non-negative number");
        }

        this.quantity = stockQuantity;
    }

    public Integer addQuantity(Integer quantity){
        if(quantity < 0){
            throw new IllegalArgumentException("Newly added quantity must be positive");
        }
        return this.quantity + quantity;
    }

    public Integer removeQuantity(Integer quantity){
        if((this.quantity - quantity) < 0){
            throw new IllegalArgumentException("Insufficient quantity");
        }

        return (this.quantity - quantity);
    }
}
