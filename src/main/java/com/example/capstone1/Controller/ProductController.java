package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Models.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    public final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getAllProducts() {
        if (productService.getProducts().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Product list is empty")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                productService.getProducts()
        );
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        boolean response = productService.addProduct(product);
        if (response) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Product added successfully")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("Product not added successfully, category not found ")
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        if (productService.updateProduct(id, product)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Product updated successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Product not found")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        if (productService.removeProduct(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Product deleted successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Product not found or empty")
            );
        }
    }

    @GetMapping("/get/byCategory/{categoryId}")
    public ResponseEntity getProductsByCategoryId(@PathVariable String categoryId) {

        if (productService.getProductsByCategoryId(categoryId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Product list in this category is empty")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategoryId(categoryId));
    }
    @GetMapping("/sort/price/ascending")
    public ResponseEntity getProductsSortedByPriceAscending() {

        if (productService.getProductsSortedByPriceAscending().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("No products available to sort")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsSortedByPriceAscending());
    }

    @GetMapping("/sort/price/descending")
    public ResponseEntity getProductsSortedByPriceDescending() {
        if (productService.getProductsSortedByPriceDescending().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("No products available to sort")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsSortedByPriceDescending());
    }
}
