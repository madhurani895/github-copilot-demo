package com.github.copilot;

import com.github.copilot.Entity.OrderEntity;
import com.github.copilot.Repository.OrderRepository;
import com.github.copilot.Service.InventoryService;
import com.github.copilot.Service.OrderService;
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

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        OrderEntity order = new OrderEntity();
        order.setProduct("Product A");
        order.setQuantity(10);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        OrderEntity createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals("Product A", createdOrder.getProduct());
        verify(inventoryService, times(1)).updateInventoryLevels("Product A", -10);
    }

    @Test
    void testGetAllOrders() {
        OrderEntity order1 = new OrderEntity();
        OrderEntity order2 = new OrderEntity();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderEntity> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
    }

    @Test
    void testGetOrderById() {
        OrderEntity order = new OrderEntity();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<OrderEntity> foundOrder = orderService.getOrderById(1L);

        assertTrue(foundOrder.isPresent());
    }

    @Test
    void testUpdateOrder() {
        OrderEntity order = new OrderEntity();
        order.setProduct("Product A");
        order.setQuantity(10);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        OrderEntity orderDetails = new OrderEntity();
        orderDetails.setProduct("Product B");
        orderDetails.setQuantity(15);

        OrderEntity updatedOrder = orderService.updateOrder(1L, orderDetails);

        assertNotNull(updatedOrder);
        assertEquals("Product B", updatedOrder.getProduct());
        assertEquals(15, updatedOrder.getQuantity());
    }

    @Test
    void testDeleteOrder() {
        OrderEntity order = new OrderEntity();
        order.setProduct("Product A");
        order.setQuantity(10);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
        verify(inventoryService, times(1)).updateInventoryLevels("Product A", 10);
    }
}
