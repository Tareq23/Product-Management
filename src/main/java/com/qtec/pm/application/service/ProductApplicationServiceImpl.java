package com.qtec.pm.application.service;

import com.qtec.pm.application.dto.ProductDto;
import com.qtec.pm.application.exception.ProductNotFoundException;
import com.qtec.pm.domain.entity.Product;
import com.qtec.pm.domain.service.ProductDomainService;
import com.qtec.pm.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductApplicationServiceImpl implements ProductDomainService {

    private final ProductRepository repository;

    @Override
    public Void createProduct(ProductDto productDto) {
        if(repository.findByName(productDto.getName()).isPresent()){
            throw new IllegalArgumentException("Product name must be unique.");
        }
        Product product = new Product(
                productDto.getName(),
                productDto.getCategory(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStockQuantity());
        repository.save(product);
        return null;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()) throw new ProductNotFoundException("Product not found for id: "+id);

        product.ifPresent(e -> {
            if(productDto.getName() != null && !e.getName().equals(productDto.getName()) && repository.findByName(productDto.getName()).isPresent()){
                throw new IllegalArgumentException("Product name must be unique.");
            }

            if(productDto.getName() != null)
                e.setName(productDto.getName());
            if(productDto.getCategory() != null)
                e.setCategory(productDto.getCategory());
            if(productDto.getDescription() != null)
                e.setDescription(productDto.getDescription());
            if(productDto.getPrice() != null)
                e.setPrice(productDto.getPrice());
            if(productDto.getStockQuantity() != null)
                e.setStockQuantity(productDto.getStockQuantity());
            repository.save(e);
        });


        return convertToDto(product.get());
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ProductDto updateStock(Long id, Integer stockQuantity) {
        if(stockQuantity < 0) throw new IllegalArgumentException("Stock quantity can't be negative");

        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()) throw new ProductNotFoundException("Product not found for id: "+id);

        product.ifPresent(e -> {
            e.setStockQuantity(stockQuantity);
            repository.save(e);
        });
        return convertToDto(product.get());
    }

    @Override
    public Optional<ProductDto> findProductById(Long id) {
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()) throw new ProductNotFoundException("Product not found for id: "+id);
        return product.map(this::convertToDto);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return repository.findAll().stream().map(product -> {
            return new ProductDto(
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStockQuantity(),
                    product.getCategory()
            );
        }).collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory()
        );
    }
}
