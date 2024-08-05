package com.example.capstone1.Service;

import com.example.capstone1.Models.MerchantStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor

public class MerchantStockService {

     ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    public final MerchantService merchantService;
    public final ProductService productService;


    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public int addMerchantStock(MerchantStock merchantStock) {

        if (productService.getProductById(merchantStock.getProductId()) == null) {
            return -1;
        }
        if (merchantService.getMerchantById(merchantStock.getMerchantId())== null){
            return -2;
        }

        merchantStocks.add(merchantStock);
        return 1;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean removeMerchantStock(String id) {
        if (merchantStocks.isEmpty()) {
            return false;
        }

        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getId().equals(id)) {
                merchantStocks.remove(merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean addMoreStocks(String merchantId , String productId, int quantity){
        if (merchantStocks.isEmpty()){
            return false;
        }
        for (MerchantStock merchantStock : merchantStocks) {
                if (merchantStock.getMerchantId().equals(merchantId) && productId.equals(merchantStock.getProductId())){
                    merchantStock.setStock(merchantStock.getStock() + quantity);
                    return true ;
            }

        }
        return false;
    }
    public MerchantStock getMerchantStockByProductAndMerchant(String productId, String merchantId) {

        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getProductId().equals(productId) && merchantStock.getMerchantId().equals(merchantId)) {
                return merchantStock;
            }
        }
        return null;
    }
    public boolean isProductInStock(String merchantId, String productId) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantId().equals(merchantId) && stock.getProductId().equals(productId)) {
                System.out.println(stock.getStock());
                return stock.getStock() > 0;
            }
        }
        return false;
    }

    public void reduceStock(String merchantId, String productId) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantId().equals(merchantId) && stock.getProductId().equals(productId)) {
                stock.setStock(stock.getStock() - 1);
                break;
            }
        }
    }










}
