package com.dat.orderservice.service.impl;

import com.dat.orderservice.config.WebClientConfig;
import com.dat.orderservice.dto.InventoryResponse;
import com.dat.orderservice.dto.OrderLineItemsDto;
import com.dat.orderservice.dto.OrderRequest;
import com.dat.orderservice.entity.Order;
import com.dat.orderservice.entity.OrderLineItems;
import com.dat.orderservice.repository.OrderLineItemsRepository;
import com.dat.orderservice.repository.OrderRepository;
import com.dat.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItemsDto> orderLineItemsDtoList = orderRequest.getOrderLineItemsDtoList();
        List<OrderLineItems> orderLineItemsList = orderLineItemsDtoList.stream().map(this::mapToOrderLineItems).toList();
        Order order = new Order();
        order.setOrderLineItemsList(orderLineItemsList);
        order.setOrderCode(UUID.randomUUID().toString());

        List<String> skuCode = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call inventory service and place order if product is in stock
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCode", skuCode)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);
        if (allProductInStock){
            orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("product is not in stock, please try again later");
        }
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItemsRepository.save(orderLineItems);
        return orderLineItems;
    }
}
