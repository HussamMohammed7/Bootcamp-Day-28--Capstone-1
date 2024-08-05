package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Models.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {

    public final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getAllMerchants() {
        if (merchantService.getMerchants().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Merchant list is empty")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                merchantService.getMerchants()
        );
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("Merchant added successfully")
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        if (merchantService.updateMerchant(id, merchant)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Merchant updated successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Merchant not found")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id) {
        if (merchantService.removeMerchant(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Merchant deleted successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Merchant not found or empty")
            );
        }
    }
}