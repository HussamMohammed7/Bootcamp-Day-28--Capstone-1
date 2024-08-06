package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Models.User;
import com.example.capstone1.Service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ResourceBundle;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    private final GiftCardService giftCardService;
    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        if (userService.getUsers().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("User list is empty")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                userService.getUsers()
        );
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("User added successfully")
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errors.getFieldError().getDefaultMessage()
            );
        }
        if (userService.updateUser(id, user)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("User updated successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("User not found")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        if (userService.removeUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("User deleted successfully")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("User not found or empty")
            );
        }
    }

//    public ResponseEntity buy(String idUser ,String idMerchant,String idProduct ){
//
//        if (userService.getUserById(idUser) == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ApiResponse("User not found")
//            );
//        }
//
//
//
//
//
//
//
//
//    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        if (userService.login(username,password) == null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("User not found or password incorrect")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.login(username,password)
        );

    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestParam String id, @RequestParam String password ,@RequestParam String confirmPassword) {

       int response = userService.resetPassword(id, password, confirmPassword) ;
       if (response == -1) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                   new ApiResponse("User not found ")
           );
       }
       if (response == -2) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                   new ApiResponse("password and confirmPassword doesnt match ")
           );
       }
       if (response == -4){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                   new ApiResponse("Password length should be more then 8 ")
           );
       }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Password reset successfully")
            );




    }

    @PostMapping("/buy")
    public ResponseEntity buyProduct(@RequestParam String userId ,@RequestParam String productId ,@RequestParam String merchantId) {

        int response = userService.buyProduct(userId,productId,merchantId);
        if (response == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("User not found or empty")
            );
        }
        if (response == -7) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("User is not a Customer")
            );
        }
        if (response ==-2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Product not found")
            );
        }
        if (response == -3){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Product out of stock")
            );
        }
        if (response == -4){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Balance is not enough")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("User buy product successfully")
        );

    }




    @GetMapping("/history/{userId}")

    public ResponseEntity history(@PathVariable String userId){

        if (userService.history(userId) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("User not found or empty")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.history(userId)
        );

    }

    @PostMapping("/message")
    public ResponseEntity message(@RequestParam String userId,@RequestParam String merchantId,
                                  @RequestParam String sender ,@RequestParam String orderId,@RequestParam String messageText){

       int result = userService.sendMessageToMerchant(userId , merchantId, sender, orderId, messageText);

        if (result == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Message not sent ,User not found or empty")
            );
        }

        if (result ==-2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Message not sent , Merchant not found")
            );
        }
        if (result == -3){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Message not sent ,Order is null")
            );
        }
        if (result == -4){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Message not sent ,Order not found")
            );
        }
       return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("Message sent")
        );






    }


    @GetMapping("get/conversation")
    public ResponseEntity getConversation(@RequestParam String userId,@RequestParam String merchantId){
        if (
                userService.getConversation(userId,merchantId) == null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse("Conversation not found")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.getConversation(userId,merchantId)
        );

    }
    @PostMapping("/renewSubscription")
    public ResponseEntity renewSubscription(@RequestParam String userId) {

        boolean result = userService.renewSubscription(userId);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Subscription renewed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to renew subscription.");
        }
    }

    @PostMapping("/createGiftCard")
    public ResponseEntity createGiftCard(double amount ) {
            boolean response = giftCardService.createGiftCard(amount);
            if (response) {
                return ResponseEntity.status(HttpStatus.OK).body("Gift card created successfully.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Failed to create gift card")
            );

    }

    @PostMapping("/redeemGiftCard")
    public ResponseEntity redeemGiftCard(
            @RequestParam String userId,
            @RequestParam String code) {
        boolean response = userService.redeemGiftCard(userId, code);

        if (response) {
            return ResponseEntity.status(HttpStatus.OK).body("Gift card redeemed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid gift card or user ID.");
        }
    }
    @GetMapping("/giftCards")
    public ResponseEntity getAllGiftCards(@RequestParam String userId) {
        if (!userService.isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                giftCardService.getAllGiftCards()
        );
    }

    @PutMapping("/updateOrderStatus/{userId}/{orderId}")
    public ResponseEntity updateOrderStatus(@PathVariable String userId, @PathVariable String orderId) {

        if (!userService.isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        boolean result = orderService.updateOrderStatus(orderId);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Order status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order status or order not found.");
        }
    }

}
