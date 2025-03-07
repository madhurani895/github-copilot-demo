package com.github.copilot;

import com.github.copilot.Entity.InventoryEntity;
import com.github.copilot.Repository.InventoryRepository;
import com.github.copilot.Service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setProduct("Product A");
        inventory.setQuantity(100);
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventory);

        InventoryEntity addedProduct = inventoryService.addProduct(inventory);

        assertNotNull(addedProduct);
        assertEquals("Product A", addedProduct.getProduct());
    }

    @Test
    void testGetAllProducts() {
        InventoryEntity inventory1 = new InventoryEntity();
        InventoryEntity inventory2 = new InventoryEntity();
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory1, inventory2));

        List<InventoryEntity> products = inventoryService.getAllProducts();

        assertEquals(2, products.size());
    }

    @Test
    void testGetProductById() {
        InventoryEntity inventory = new InventoryEntity();
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        Optional<InventoryEntity> foundProduct = inventoryService.getProductById(1L);

        assertTrue(foundProduct.isPresent());
    }

    @Test
    void testUpdateProduct() {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setProduct("Product A");
        inventory.setQuantity(100);
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventory);

        InventoryEntity inventoryDetails = new InventoryEntity();
        inventoryDetails.setProduct("Product B");
        inventoryDetails.setQuantity(150);

        InventoryEntity updatedProduct = inventoryService.updateProduct(1L, inventoryDetails);

        assertNotNull(updatedProduct);
        assertEquals("Product B", updatedProduct.getProduct());
    }

    @Test
    void testDeleteProduct() {
        InventoryEntity inventory = new InventoryEntity();
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        inventoryService.deleteProduct(1L);

        verify(inventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateInventoryLevels() {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setProduct("Product A");
        inventory.setQuantity(100);
        when(inventoryRepository.findByProduct("Product A")).thenReturn(Optional.of(inventory));

        inventoryService.updateInventoryLevels("Product A", -10);

        assertEquals(90, inventory.getQuantity());
        verify(inventoryRepository, times(1)).save(inventory);
    }
}