package ch.halil.cankilic.gymprodukteapp.controller;

import ch.halil.cankilic.gymprodukteapp.entity.Product;
import ch.halil.cankilic.gymprodukteapp.security.Roles;
import ch.halil.cankilic.gymprodukteapp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/products")
@Tag(name = "Admin Products", description = "Endpoints for managing products (Admin only)")
@SecurityRequirement(name = "bearerAuth")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product (Admin only)")
    @PostMapping
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @Operation(summary = "Update an existing product (Admin only)")
    @PutMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestBody Product product,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a product (Admin only)")
    @DeleteMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a product by ID (Admin only)")
    @GetMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
