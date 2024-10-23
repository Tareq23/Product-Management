package com.qtec.pm.application.controller;


import com.qtec.pm.application.dto.ProductDto;
import com.qtec.pm.application.service.ProductApplicationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductApplicationServiceImpl productApplicationService;



    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto product) {
        productApplicationService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        return productApplicationService.findAllProducts();
    }

    @GetMapping("/products/{id}")
    public Optional<ProductDto> getProductById(@PathVariable("id") Long id){
        return productApplicationService.findProductById(id);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id){
        productApplicationService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProductInfo(@PathVariable("id") Long id, @RequestBody ProductDto productDto){
        productApplicationService.updateProduct(id, productDto);
        return ResponseEntity.ok(productApplicationService.updateProduct(id, productDto));
    }

    @PatchMapping("/products/{id}/update-stock")
    public ResponseEntity<ProductDto> updateStockQuantity(@PathVariable("id") Long id,  @RequestParam Integer stockQuantity){
        return ResponseEntity.ok(productApplicationService.updateStock(id, stockQuantity));
    }
}
