package com.movieticket.controller;

import com.movieticket.entity.Order;
import com.movieticket.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id, Authentication authentication) {
        Order order = orderService.getOrderById(id);
        if (isAdmin(authentication) || order.getUserId().equals(authentication.getName())) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.status(201).body(orderService.placeOrder(order));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String id, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().body("Status is required");
        }
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok("Order status updated successfully!");
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
    }
}