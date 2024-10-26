package com.qtec.pm.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
@Getter
@NoArgsConstructor
public class Discount {

    @Column(name = "discount")
    private BigDecimal percentage = BigDecimal.ZERO;

    public Discount(BigDecimal percentage){
        if(percentage.compareTo(BigDecimal.ZERO) < 0 ||
            percentage.compareTo(BigDecimal.valueOf(70)) > 0
        ){
            throw new IllegalArgumentException("Discount percentage must be between 0 and 70");
        }
        this.percentage = percentage;
    }

    public BigDecimal applyDiscount(BigDecimal price){
        return price.subtract(price.multiply(percentage).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
    }
}
