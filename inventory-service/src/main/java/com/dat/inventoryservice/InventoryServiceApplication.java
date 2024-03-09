package com.dat.inventoryservice;

import com.dat.inventoryservice.entity.Inventory;
import com.dat.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
				Inventory inventory = new Inventory();
				inventory.setSkuCode("iphone13");
				inventory.setQuantity(10);

				Inventory inventory1 = new Inventory();
				inventory1.setSkuCode("iphone14");
				inventory1.setQuantity(0);

				inventoryRepository.save(inventory);
				inventoryRepository.save(inventory1);
		};
	}

}
