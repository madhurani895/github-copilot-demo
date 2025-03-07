package com.github.copilot.Service;

import com.github.copilot.Entity.InventoryEntity;
import com.github.copilot.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InventoryService {
    private static final Logger logger = Logger.getLogger(InventoryService.class.getName());

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryEntity addProduct(InventoryEntity inventory) {
        logger.log(Level.INFO, "Adding product: {0}", inventory.getProduct());
        return inventoryRepository.save(inventory);
    }

    public List<InventoryEntity> getAllProducts() {
        logger.log(Level.INFO, "Fetching all products");
        return inventoryRepository.findAll();
    }

    public Optional<InventoryEntity> getProductById(Long id) {
        logger.log(Level.INFO, "Fetching product by ID: {0}", id);
        return inventoryRepository.findById(id);
    }
    public Optional<InventoryEntity> getProductByName(String name) {
        logger.log(Level.INFO, "Fetching product by Name: {0}", name);
        return inventoryRepository.findByProduct(name);
    }

    public InventoryEntity updateProduct(Long id, InventoryEntity inventoryDetails) {
        logger.log(Level.INFO, "Updating product with ID: {0}", id);
        InventoryEntity inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        inventory.setProduct(inventoryDetails.getProduct());
        inventory.setQuantity(inventoryDetails.getQuantity());
        return inventoryRepository.save(inventory);
    }

    public void deleteProduct(Long id) {
        logger.log(Level.INFO, "Deleting product with ID: {0}", id);
        inventoryRepository.deleteById(id);
    }

    public void updateInventoryLevels(String product, int quantityChange) {
//        logger.log(Level.INFO, "Updating inventory levels for product: {0} by {1}", new Object[]{product, quantityChange});
        InventoryEntity inventory = inventoryRepository.findByProduct(product).orElseThrow(() -> new RuntimeException("Product not found"));
        inventory.setQuantity(inventory.getQuantity() + quantityChange);
        inventoryRepository.save(inventory);
    }

    @Scheduled(cron = "0 0 * * * ?") // This cron expression schedules the method to run every hour
    public void notifyInventoryLow() {
        logger.log(Level.INFO, "Checking inventory levels");
        List<InventoryEntity> inventoryList = inventoryRepository.findAll();
        if (inventoryList.size() < 10) {
            logger.log(Level.WARNING, "Inventory is low");
        }
    }
}