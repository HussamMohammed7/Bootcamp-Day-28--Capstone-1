package com.example.capstone1.Service;

import com.example.capstone1.Models.Orders;
import com.example.capstone1.Models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    static ArrayList<Orders> orders = new ArrayList<>();

    public Orders getOrderById(String orderId) {
        for (Orders order : orders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
    public void addOrder(Orders order) {
        orders.add(order);
    }



    public boolean updateOrderStatus(String orderId) {
        Orders order = getOrderById(orderId);
        if (order == null) {
            return false;
        }

        switch (order.getStatus()) {
            case "under process":
                order.setStatus("shipped");
                break;
            case "shipped":
                order.setStatus("finished");
                break;
            case "finished":
                return false;
            default:
                return false;
        }
        return true;
    }


}
