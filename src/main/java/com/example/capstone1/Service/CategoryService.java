package com.example.capstone1.Service;

import com.example.capstone1.Models.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

     ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public boolean updateCategory(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean removeCategory(String id) {
        if (categories.isEmpty()) {
            return false;
        }

        for (Category category : categories) {
            if (category.getId().equals(id)) {
                categories.remove(category);
                return true;
            }
        }
        return false;
    }
    public Category getCategoryBy(String id) {
        for (Category category : categories) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }


}