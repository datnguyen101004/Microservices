package com.dat.productservice.service;

import com.dat.productservice.dto.ProductRequest;
import com.dat.productservice.dto.ProductResponse;
import com.dat.productservice.entity.Product;

import java.util.List;

public interface ProductService {
    void addProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
