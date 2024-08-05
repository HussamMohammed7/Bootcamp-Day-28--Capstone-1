package com.example.capstone1.Models;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GiftCard {

    @NotEmpty(message = "Code cannot be empty")
    private String code;

    @Min(value = 0, message = "Amount must be positive")
    private double amount;

    @AssertFalse
    private boolean redeemed;
}
