package com.github.copilot;

import com.github.copilot.Entity.InventoryEntity;
import com.github.copilot.Entity.OrderEntity;
import com.github.copilot.Service.InventoryService;
import com.github.copilot.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderInventoryIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        // Add sample inventory data
        InventoryEntity inventory1 = new InventoryEntity();
        inventory1.setProduct("Product A");
        inventory1.setQuantity(100);
        inventoryService.addProduct(inventory1);

        InventoryEntity inventory2 = new InventoryEntity();
        inventory2.setProduct("Product B");
        inventory2.setQuantity(50);
        inventoryService.addProduct(inventory2);
    }

    @Test
    void testCreateOrderAndUpdateInventory() {
        // Create a new order
        OrderEntity order = new OrderEntity();
        order.setProduct("Product A");
        order.setQuantity(10);
        OrderEntity createdOrder = orderService.createOrder(order);

        // Verify the order is created
        assertNotNull(createdOrder);
        assertEquals("Product A", createdOrder.getProduct());

        // Verify the inventory is updated
        InventoryEntity inventory = inventoryService.getProductByName(createdOrder.getProduct()).orElseThrow(() -> new RuntimeException("Product not found"));
        assertEquals(90, inventory.getQuantity());
    }

    @Test
    void testUpdateOrderAndUpdateInventory() {
        // Create a new order
        OrderEntity order = new OrderEntity();
        order.setProduct("Product A");
        order.setQuantity(10);
        OrderEntity createdOrder = orderService.createOrder(order);

        // Update the order
        OrderEntity orderDetails = new OrderEntity();
        orderDetails.setProduct("Product A");
        orderDetails.setQuantity(15);
        OrderEntity updatedOrder = orderService.updateOrder(createdOrder.getId(), orderDetails);

        // Verify the order is updated
        assertNotNull(updatedOrder);
        assertEquals(15, updatedOrder.getQuantity());

        // Verify the inventory is updated
        InventoryEntity inventory = inventoryService.getProductByName(updatedOrder.getProduct()).orElseThrow(() -> new RuntimeException("Product not found"));
        assertEquals(95, inventory.getQuantity());
    }

    @Test
    void testDeleteOrderAndUpdateInventory() {
        // Create a new order
        OrderEntity order = new OrderEntity();
        order.setProduct("Product A");
        order.setQuantity(10);
        OrderEntity createdOrder = orderService.createOrder(order);

        // Delete the order
        orderService.deleteOrder(createdOrder.getId());

        // Verify the inventory is updated
        InventoryEntity inventory = inventoryService.getProductById(createdOrder.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        assertEquals(100, inventory.getQuantity());
    }
}