package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Models.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/merchant-stocks")
@RequiredArgsConstructor

public class MerchantStockController {

    public final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getAllMerchantStocks() {
        if (merchantStockService.getMerchantStocks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Merchant stock list is empty")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                merchantStockService.getMerchantStocks()
        );
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        int response = merchantStockService.addMerchantStock(merchantStock);

        if (response == -1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("product id not found")
            );
        }
        if (response == -2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("merchant id not found")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("Merchant stock added successfully")
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        if (merchantStockService.updateMerchantStock(id, merchantStock)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Merchant stock updated successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Merchant stock not found")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id) {
        if (merchantStockService.removeMerchantStock(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Merchant stock deleted successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Merchant stock not found or empty")
            );
        }
    }


    @PutMapping("/add/stock")
    public ResponseEntity addStock(@RequestParam String merchantId,@RequestParam String productId, @RequestParam int quantity) {
        if (merchantStockService.addMoreStocks(productId,merchantId,quantity)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Merchant stock added by  "+quantity+" successfully")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("Merchant stock not found or empty")
        );
    }

}
