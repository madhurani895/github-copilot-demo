package com.github.copilot.Controller;

import com.github.copilot.Entity.OrderEntity;
import com.github.copilot.Exception.ResourceNotFoundException;
import com.github.copilot.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = Logger.getLogger(OrderController.class.getName());

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public OrderEntity createOrder(@Valid @RequestBody OrderEntity order) {
        logger.log(Level.INFO, "Creating order for product: {0}", order.getProduct());
        return orderService.createOrder(order);
    }

    @GetMapping("/getAllOrders")
    public List<OrderEntity> getAllOrders() {
        logger.log(Level.INFO, "Fetching all orders");
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderEntity getOrderById(@PathVariable Long id) {
        logger.log(Level.INFO, "Fetching order by ID: {0}", id);
        return orderService.getOrderById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrder(@PathVariable Long id, @RequestBody OrderEntity orderDetails) {
        logger.log(Level.INFO, "Updating order with ID: {0}", id);
        return orderService.updateOrder(id, orderDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        logger.log(Level.INFO, "Deleting order with ID: {0}", id);
        orderService.deleteOrder(id);
    }
}