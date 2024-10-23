package com.qtec.pm.domain.service;

import com.qtec.pm.application.dto.ProductDto;
import com.qtec.pm.domain.entity.Product;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface ProductDomainService {

    Void createProduct(ProductDto product);
    ProductDto updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
    ProductDto updateStock(Long id, Integer stockQuantity);
    Optional<ProductDto> findProductById(Long id) throws ChangeSetPersister.NotFoundException;
    List<ProductDto> findAllProducts();
}
