package com.qtec.pm.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@Embeddable
@NoArgsConstructor
public class Category {

    @Column(name = "category")
    private String name;

    public Category(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Category name can't be empty");
        }
        this.name = name;
    }
}
