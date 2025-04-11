package ch.halil.cankilic.gymprodukteapp.controller;

import ch.halil.cankilic.gymprodukteapp.entity.Order;
import ch.halil.cankilic.gymprodukteapp.security.Roles;
import ch.halil.cankilic.gymprodukteapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Endpoints for users to view their own orders")
@SecurityRequirement(name = "bearerAuth") // Global security requirement for the controller
public class UserController {

    private final OrderService orderService;

    public UserController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Retrieve orders for the current user", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/orders")
    @RolesAllowed({Roles.Read, Roles.Admin})
    public List<Order> getUserOrders() {
        // In a full implementation, filter orders by the authenticated user.
        return orderService.getAllOrders();
    }
}