package com.github.copilot.Service;

import com.github.copilot.Entity.OrderEntity;
import com.github.copilot.Exception.ResourceNotFoundException;
import com.github.copilot.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class OrderService {
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    public OrderEntity createOrder(OrderEntity order) {
        logger.log(Level.INFO, "Creating order for product: {0}", order.getProduct());
        order.setStatus("CREATED");
        OrderEntity savedOrder = orderRepository.save(order);
        inventoryService.updateInventoryLevels(order.getProduct(), -order.getQuantity());
        return savedOrder;
    }

    public List<OrderEntity> getAllOrders() {
        logger.log(Level.INFO, "Fetching all orders");
        return orderRepository.findAll();
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        logger.log(Level.INFO, "Fetching order by ID: {0}", id);
        return orderRepository.findById(id);
    }

    public OrderEntity updateOrder(Long id, OrderEntity orderDetails) {
        logger.log(Level.INFO, "Updating order with ID: {0}", id);
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        int quantityDifference = orderDetails.getQuantity() - order.getQuantity();
        order.setProduct(orderDetails.getProduct());
        order.setQuantity(orderDetails.getQuantity());
        order.setStatus("Updated");
        OrderEntity updatedOrder = orderRepository.save(order);
        inventoryService.updateInventoryLevels(order.getProduct(), quantityDifference);
        return updatedOrder;
    }

    public void deleteOrder(Long id) {
        logger.log(Level.INFO, "Deleting order with ID: {0}", id);
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        inventoryService.updateInventoryLevels(order.getProduct(), order.getQuantity());
        orderRepository.deleteById(id);
    }
}