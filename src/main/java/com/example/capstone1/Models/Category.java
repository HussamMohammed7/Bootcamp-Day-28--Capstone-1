package com.example.capstone1.Models;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {


    @NotEmpty(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 4 , message = "have to be more than 3 length long")
    private String name;






}
