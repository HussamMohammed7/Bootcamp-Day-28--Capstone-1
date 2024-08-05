package com.example.capstone1.Service;

import com.example.capstone1.Models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {

    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    private final GiftCardService giftCardService;
    private final OrderService orderService;


    ArrayList<User> users = new ArrayList<>();

    private final MerchantService merchantService;


    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (user.getOrders()== null) {
            user.setOrders(new ArrayList<>());
        }
        if (user.getMessagesList()== null) {
            user.setMessagesList(new ArrayList<>());
        }
        users.add(user);
    }

    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean removeUser(String id) {
        if (users.isEmpty()) {
            return false;
        }

        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }


    public User login (String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && !user.getPassword().equals(password)) {
                return null;
            }
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;

    }

//    public Boolean addToCart(String userId, String productId) {
//        User user = getUserById(userId);
//        if (user == null) {
//            return false;
//        }
//        Product product = productService.getProductById(productId);
//        if (product == null) {
//            return false;
//        }
//        user.getCart().add(product);
//        return true;
//    }



    public int buyProduct(String userId ,String productId ,String merchantId) {

        if (users.isEmpty()) {
            return -1;
        }
        User currentUser = getUserById(userId);
        if (isAdmin( userId)){
            return -7;
        }
        if (currentUser==null){
            return -1;
        }
        if (merchantStockService.getMerchantStockByProductAndMerchant(productId, merchantId) == null) {
            return -2;
        }
        if (!merchantStockService.isProductInStock(merchantId, productId)){
            return -3;
        }

        double price_product = productService.getProductById(productId).getPrice();
        if (currentUser.getSubscribed().equals("prime"))
        {
            price_product = price_product-(price_product*0.15);
        }
        System.out.println(price_product);

        if(!balanceDeposit(userId,price_product)){
            return -4;
        }
        merchantStockService.reduceStock(merchantId,productId);

        Random random = new Random();
        int randomNumber = random.nextInt(1000000) + 1;

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(productService.getProductById(productId));

        Orders order = new Orders(
                Integer.toString(randomNumber),
                productList,
                1,
                "under process",
                price_product,
                LocalDateTime.now()
        );
        if (currentUser.getOrders()== null) {
            currentUser.setOrders(new ArrayList<>());
        }
        currentUser.getOrders().add(order);


        orderService.addOrder(order);
        return 0;


    }


    public boolean balanceDeposit(String userId, double amount) {
        User user = getUserById(userId);
        if (user != null && user.getBalance() >= amount) {
            System.out.println((user.getBalance() - amount));
            user.setBalance(user.getBalance() - amount);
            return true;
        }
        return false;
    }

    public ArrayList<Orders> history(String userId) {
        User user = getUserById(userId);

        if (user != null) {
            if (user.getOrders()== null) {
                user.setOrders(new ArrayList<>());
            }
            return user.getOrders();
        }

        return null;
    }

    public int sendMessageToMerchant(String userId, String merchantId,String sender ,String orderId, String messageText) {

        User user = getUserById(userId);
        if (user== null) {
            return -1;
        }
        if(merchantService.getMerchantById(merchantId)==null){
            return -2;
        }
        if (user.getOrders()== null ) {
            return -3;
        }
        boolean result =false ;
        System.out.println(orderId);

        for (Orders order : user.getOrders()) {
            if (order.getId().equals(orderId)) {
                System.out.println(orderId);
                result = true;
                break;
            }
        }
        if (!result ){
            return -4;
        }

        Messages message = new Messages(
                UUID.randomUUID().toString(),
                messageText,
                sender,
                merchantId,
                orderId,
                userId
        );


        user.getMessagesList().add(message);
        return 0;
    }

    public ArrayList<Messages> getConversation (String userId , String merchantId) {

        ArrayList<Messages> messagesList = new ArrayList<>();

        User user = getUserById(userId);
        if (user == null) {
            return null;

        }
        for (Messages message : user.getMessagesList()) {
            if (message.getIdUser().equals(userId) && message.getIdMerchant().equals(merchantId)) {
                messagesList.add(message);
            }
        }
        return messagesList;

    }
    public boolean renewSubscription(String userId) {
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        if (user.getSubscribedDateFinish() == null || user.getSubscribedDateFinish().isBefore(currentDate)) {
            user.setSubscribedDateFinish(currentDate.plusMonths(1));
        } else {
            user.setSubscribedDateFinish(user.getSubscribedDateFinish().plusMonths(1));
        }
        return true;
    }

    public boolean redeemGiftCard(String userId, String code) {
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }

        return giftCardService.redeemGiftCard(user, code);
    }

    public boolean isAdmin(String userId) {
        User user = getUserById(userId);
        return user != null && "Admin".equals(user.getRole());
    }
}