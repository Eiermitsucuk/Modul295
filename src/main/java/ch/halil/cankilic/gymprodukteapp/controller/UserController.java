package ch.halil.cankilic.gymprodukteapp.controller;

import ch.halil.cankilic.gymprodukteapp.entity.Order;
import ch.halil.cankilic.gymprodukteapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Endpoints for users to view their own orders")
public class UserController {

    private final OrderService orderService;

    public UserController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Retrieve orders for the current user")
    @GetMapping("/orders")
    @PreAuthorize("hasRole('USER')")
    public List<Order> getUserOrders() {
        // In a full implementation, filter orders by the authenticated user.
        return orderService.getAllOrders();
    }
}
