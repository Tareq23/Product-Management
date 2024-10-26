package com.qtec.pm.application.dto;

import com.qtec.pm.domain.valueobject.Discount;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class DiscountProductDto extends ProductDto{

    private BigDecimal currentPrice;
    public DiscountProductDto(String name, String description, BigDecimal price, Integer stockQuantity, String category, BigDecimal percentage) {
        super(name, description, price, stockQuantity, category, percentage);
        this.currentPrice = new Discount(percentage).applyDiscount(price);
    }
}
