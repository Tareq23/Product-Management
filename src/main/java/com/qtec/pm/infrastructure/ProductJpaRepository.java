package com.qtec.pm.infrastructure;

import com.qtec.pm.domain.entity.Product;
import com.qtec.pm.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends  ProductRepository{
}
