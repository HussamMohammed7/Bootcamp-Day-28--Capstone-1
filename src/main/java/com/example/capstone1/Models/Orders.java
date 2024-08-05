package com.example.capstone1.Models;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor

public class Orders {


    @NotEmpty(message = "Order ID cannot be empty")
    private String id;

    @NotEmpty(message = "Products cannot be empty")
    private ArrayList<Product> products;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;

    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "under process|shipped|finished|canceled",
            message = "Status must be one of 'under process', 'shipped', 'finished', 'canceled'")
    private String status;

    @NotNull(message = "total cannot be null")
    private double total ;
    @NotNull(message = "Purchased date and time cannot be null")
    private LocalDateTime purchasedAt;


}
