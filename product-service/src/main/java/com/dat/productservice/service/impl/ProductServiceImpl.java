package com.dat.productservice.service.impl;

import com.dat.productservice.dto.ProductRequest;
import com.dat.productservice.dto.ProductResponse;
import com.dat.productservice.entity.Product;
import com.dat.productservice.repository.ProductRepository;
import com.dat.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public void addProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setPrice(productRequest.getPrice());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> productResponseList = productList.stream().map(this::mapToProductResponse).toList();
        return productResponseList;
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }
}
