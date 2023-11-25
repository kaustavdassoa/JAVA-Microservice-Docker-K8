package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.exception.InsufficientStockException;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.model.ProductRequest;
import com.example.productservice.model.ProductResponse;
import com.example.productservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepo;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product..");

        Product product
                = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepo.save(product);

        log.info("Product Created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        Product product= productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product ID# "+productId+" not found"));

        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {

        log.info("Reduce Quantity {} for Id: {}", quantity,productId);

        Product product= productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product ID# "+productId+" not found"));

        if(product.getQuantity() < quantity)
            throw new InsufficientStockException("Insufficient Stock for Product ID # "+productId,
                    HttpStatus.BAD_REQUEST);

        long reduceQuantity= product.getQuantity()-quantity;
        product.setQuantity(reduceQuantity);

        productRepo.save(product);
        log.info("reduceQuantity for productID # {} newQuantity = {}",productId,reduceQuantity);

    }
}
