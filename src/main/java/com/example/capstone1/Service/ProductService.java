package com.example.capstone1.Service;

import com.example.capstone1.Models.Category;
import com.example.capstone1.Models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final CategoryService categoryService;
    ArrayList<Product> products = new ArrayList<>();




    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product) {

        Category checkCat= categoryService.getCategoryBy(product.getCategoryID());
        if (checkCat == null) {
            return false;
        }
        products.add(product);
        return true;
    }

    public boolean updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(String id) {
        if (products.isEmpty()) {
            return false;
        }

        for (Product product : products) {
            if (product.getId().equals(id)) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getProductsByCategoryId(String categoryId) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategoryID().equals(categoryId)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public ArrayList<Product> getProductsSortedByPriceAscending() {
        ArrayList<Product> sortedProducts = new ArrayList<>(products);
        selectionSortByPrice(sortedProducts, true);
        return sortedProducts;
    }

    public ArrayList<Product> getProductsSortedByPriceDescending() {
        ArrayList<Product> sortedProducts = new ArrayList<>(products);
        selectionSortByPrice(sortedProducts, false);
        return sortedProducts;
    }

    private void selectionSortByPrice(ArrayList<Product> products, boolean ascending) {

        for (int i = 0; i < products.size() - 1; i++) {
            int indexSwap = i;
            for (int j = i + 1; j < products.size(); j++) {
                if (ascending) {
                    if (products.get(j).getPrice() < products.get(indexSwap).getPrice()) {
                        indexSwap = j;
                    }
                } else {
                    if (products.get(j).getPrice() > products.get(indexSwap).getPrice()) {
                        indexSwap = j;
                    }
                }
            }

            Product temp = products.get(i);
            products.set(i, products.get(indexSwap));
            products.set(indexSwap, temp);
        }
    }
    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> discountProductsOver(double price, double discountPercentage) {
        ArrayList<Product> discountedProducts = new ArrayList<>();

        for (Product product : getProducts()) {
            if (product.getPrice() >= price) {
                double discountedPrice = product.getPrice() -(product.getPrice() * 1-( discountPercentage / 100));
                product.setPrice(discountedPrice);
                discountedProducts.add(product);
            }
        }
        return discountedProducts;
    }

    public String deleteAllProductsCategory(String categoryId) {
        ArrayList<Product> filteredProducts = new ArrayList<>(products);
        for (Product product : filteredProducts) {
            if (product.getCategoryID().equals(categoryId)) {
                products.remove(product);
            }

        }
        return "category deleted";
    }

}