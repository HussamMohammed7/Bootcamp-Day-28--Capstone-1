package com.example.capstone1.Models;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor

public class User {


    @NotEmpty(message = "ID cannot be empty")
    private String id ;

    @NotEmpty(message = "username cannot be empty")
    @Size(min = 6, message = "Password must be more than 5 characters long")
    private String username ;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 7, message = "Password must be more than 6 characters long")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain both characters and digits")
    private String password;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "write a proper email ")
    private String email ;

    @NotEmpty(message = "role cannot be empty")
    @Pattern(regexp = "Admin|Customer", message = "Role must be either 'Admin' or 'Customer'")

    private String role ;


    @NotNull(message = "balance shouldent be Null ")
    @Positive(message = "Balance should be positive")

    private double balance ;

    @Pattern(regexp = "regular|prime")

    @NotNull(message = "subscribed shouldent be Null ")
    private String subscribed ;

    private LocalDate subscribedDateFinish;

    private ArrayList<Orders> orders ;
    private ArrayList<Messages> messagesList ;
    private ArrayList<Product> cart ;



}
