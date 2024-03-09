package com.dat.orderservice.controller;

import com.dat.orderservice.dto.OrderRequest;
import com.dat.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        try {
            orderService.placeOrder(orderRequest);
            return "Order placed successfully";
        }
        catch (Exception e){
            return "Error occur";
        }
    }
}
