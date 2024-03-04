package com.dat.productservice.controller;

import com.dat.productservice.dto.ProductRequest;
import com.dat.productservice.dto.ProductResponse;
import com.dat.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class Controller {
    private final ProductService productService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addProduct(@RequestBody ProductRequest productRequest){
        try {
            productService.addProduct(productRequest);
            return "Product add successfully";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

}
