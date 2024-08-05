package com.example.capstone1.Service;

import com.example.capstone1.Models.GiftCard;
import com.example.capstone1.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GiftCardService {

    static ArrayList<GiftCard> giftCards = new ArrayList<>();

    public boolean createGiftCard(double amount) {
        if (amount <= 0) {
            return false;
        }
        String code = generateGiftCardCode();
        GiftCard giftCard = new GiftCard(code, amount, false);
        giftCards.add(giftCard);
        return true;

    }

    private String generateGiftCardCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000000) + 1;
        return "Hussam"+randomNumber;
    }


    public GiftCard validateGiftCard(String code) {
        for (GiftCard giftCard : giftCards) {
            if (giftCard.getCode().equals(code) && !giftCard.isRedeemed()) {
                return giftCard;
            }
        }
        return null;
    }

    public boolean redeemGiftCard(User user, String code) {
        GiftCard giftCard = validateGiftCard(code);
        if (giftCard == null) {
            return false;
        }

        user.setBalance(user.getBalance() + giftCard.getAmount());
        giftCard.setRedeemed(true);
        return true;
    }
    public ArrayList<GiftCard> getAllGiftCards() {
        return giftCards;
    }
}
