package com.dat.inventoryservice.service;

import com.dat.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> checkInStock(List<String> skuCode);
}
