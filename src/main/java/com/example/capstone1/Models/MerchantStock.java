package com.example.capstone1.Models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class MerchantStock {


    @NotEmpty(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "productId cannot be empty")
    private String productId;

    @NotEmpty(message = "merchantId cannot be empty")
    private String merchantId;


    @NotNull(message = "price cannot be empty" )
    @Positive(message = "number must be positive ")
    @Min(value = 10 , message = "Minimum number is 10")
    private int stock;



}
