package com.example.capstone1.Models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Messages {


    private String id ;
    private String message ;
    private String sender;
    private String idMerchant;
    private String idOrder;
    private String idUser;

}
