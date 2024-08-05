package com.example.capstone1.Models;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Product {



    @NotEmpty(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 4 , message = "have to be more than 3 length long")
    private String name;

    @NotNull(message = "price cannot be empty" )
    @Positive(message = "number must be positive ")
    private double price;

    @NotNull(message = "categoryID cannot be empty" )
    private String categoryID;

}
