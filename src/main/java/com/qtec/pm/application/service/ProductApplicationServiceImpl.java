package com.qtec.pm.application.service;

import com.qtec.pm.application.dto.DiscountProductDto;
import com.qtec.pm.application.dto.PageSortDto;
import com.qtec.pm.application.dto.ProductDto;
import com.qtec.pm.application.exception.ProductNotFoundException;
import com.qtec.pm.application.utility.CommonUtility;
import com.qtec.pm.domain.entity.Product;
import com.qtec.pm.domain.repository.ProductRepository;
import com.qtec.pm.domain.service.ProductDomainService;
import com.qtec.pm.domain.valueobject.Category;
import com.qtec.pm.domain.valueobject.Discount;
import com.qtec.pm.domain.valueobject.Price;
import com.qtec.pm.domain.valueobject.StockQuantity;
import com.qtec.pm.infrastructure.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductApplicationServiceImpl implements ProductDomainService {

    private final CommonUtility commonUtility;

    private final ProductJpaRepository repository;

    @Override
    public Void createProduct(ProductDto productDto) {
        if(repository.findByName(productDto.getName()).isPresent()){
            throw new IllegalArgumentException("Product name must be unique.");
        }
        Product product = new Product(
                productDto.getName(),
                new Category(productDto.getCategory()),
                productDto.getDescription(),
                new Price(productDto.getPrice()),
                new StockQuantity(productDto.getStockQuantity()),
                new Discount(productDto.getPercentage())
        );
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
                e.setCategory(new Category(productDto.getCategory()));
            if(productDto.getDescription() != null)
                e.setDescription(productDto.getDescription());
            if(productDto.getPrice() != null)
                e.setPrice(new Price(productDto.getPrice()));
            if(productDto.getStockQuantity() != null)
                e.setStockQuantity(new StockQuantity(productDto.getStockQuantity()));
            if(productDto.getPercentage() != null)
                e.setDiscount(new Discount(productDto.getPercentage()));
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
            e.setStockQuantity(new StockQuantity(stockQuantity));
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
    public Page<ProductDto> findAllProducts(PageSortDto pageSortDto) {

        Pageable pageable = commonUtility.createPageable(pageSortDto.getPage(),
                pageSortDto.getSize(),
                pageSortDto.getSortBy(),
                pageSortDto.getSortDirection());
        return repository.findAll(pageable).map(this::convertToDto);
    }

    private ProductDto convertToDto(Product product) {
        return new DiscountProductDto(
                product.getName(),
                product.getDescription(),
                product.getPrice().getPrice(),
                product.getStockQuantity().getQuantity(),
                product.getCategory().getName(),
                product.getDiscount().getPercentage()
        );
    }
}
