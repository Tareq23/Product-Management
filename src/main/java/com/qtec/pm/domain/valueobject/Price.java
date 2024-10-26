package com.qtec.pm.domain.valueobject;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@Getter
public class Price {

    @Column(name="price")
    private BigDecimal price;

    public Price(BigDecimal price){

        if(price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price should not be negative number");
        }

        this.price = price;
    }

}
