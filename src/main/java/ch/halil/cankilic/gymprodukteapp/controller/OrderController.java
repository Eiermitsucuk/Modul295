package ch.halil.cankilic.gymprodukteapp.controller;

import ch.halil.cankilic.gymprodukteapp.entity.Order;
import ch.halil.cankilic.gymprodukteapp.security.Roles;
import ch.halil.cankilic.gymprodukteapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Retrieve all orders (Admin only)")
    @GetMapping
    @RolesAllowed(Roles.Admin)
    @SecurityRequirement(name = "bearerAuth")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Retrieve an order by ID (Admin or User)")
    @GetMapping("/{id}")
    @RolesAllowed({Roles.Admin, Roles.Read})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new order (Everyone)")
    @PostMapping
    @RolesAllowed({Roles.Admin, Roles.Update, Roles.Read})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @Operation(summary = "Update an existing order (User only)")
    @PutMapping("/{id}")
    @RolesAllowed({Roles.Update, Roles.Admin, Roles.Read})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            return ResponseEntity.ok(orderService.updateOrder(id, order));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an order (Admin only)")
    @DeleteMapping("/{id}")
    @RolesAllowed({Roles.Admin})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}