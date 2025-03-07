package com.github.copilot.Controller;

import com.github.copilot.Entity.InventoryEntity;
import com.github.copilot.Service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private static final Logger logger = Logger.getLogger(InventoryController.class.getName());

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/addProduct")
    public InventoryEntity addProduct(@Valid @RequestBody InventoryEntity inventory) {
        logger.log(Level.INFO, "Adding product: {0}", inventory.getProduct());
        return inventoryService.addProduct(inventory);
    }

    @GetMapping("getAllProducts")
    public List<InventoryEntity> getAllProducts() {
        logger.log(Level.INFO, "Fetching all products");
        return inventoryService.getAllProducts();
    }

    @GetMapping("/{id}")
    public InventoryEntity getProductById(@PathVariable Long id) {
        logger.log(Level.INFO, "Fetching product by ID: {0}", id);
        return inventoryService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PutMapping("/{id}")
    public InventoryEntity updateProduct(@PathVariable Long id, @Valid @RequestBody InventoryEntity inventoryDetails) {
        logger.log(Level.INFO, "Updating product with ID: {0}", id);
        return inventoryService.updateProduct(id, inventoryDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        logger.log(Level.INFO, "Deleting product with ID: {0}", id);
        inventoryService.deleteProduct(id);
    }
}