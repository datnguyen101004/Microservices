package com.dat.orderservice.controller;

import com.dat.orderservice.dto.OrderRequest;
import com.dat.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }

    public String fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return "Oops, something went wrongs, please wait sometime and try again";
    }
}
