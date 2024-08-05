package com.example.capstone1.Service;

import com.example.capstone1.Models.Merchant;
import com.example.capstone1.Models.Messages;
import com.example.capstone1.Models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public boolean updateMerchant(String id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    public boolean removeMerchant(String id) {
        if (merchants.isEmpty()) {
            return false;
        }

        for (Merchant merchant : merchants) {
            if (merchant.getId().equals(id)) {
                merchants.remove(merchant);
                return true;
            }
        }
        return false;
    }


    public Merchant getMerchantById(String id) {
        for (Merchant merchant : merchants) {
            if (merchant.getId().equals(id)) {
                return merchant;
            }
        }
        return null;
    }

    public ArrayList<Messages> getConversation (String userId , String merchantId) {

        ArrayList<Messages> messagesList = new ArrayList<>();

        Merchant merchant = getMerchantById(merchantId);
        if (merchant == null) {
            return null;

        }
        for (Messages message : merchant.getMessagesList()) {
            if (message.getIdUser().equals(userId) && message.getIdMerchant().equals(merchantId)) {
                messagesList.add(message);
            }
        }
        return messagesList;

    }
}