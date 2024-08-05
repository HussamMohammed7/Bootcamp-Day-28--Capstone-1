package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Models.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    public final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getAllCategories() {
        if (categoryService.getCategories().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Category list is empty")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getCategories()
        );
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("Category added successfully")
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        if (categoryService.updateCategory(id, category)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Category updated successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Category not found")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id) {
        if (categoryService.removeCategory(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Category deleted successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Category not found or empty")
            );
        }
    }
}
