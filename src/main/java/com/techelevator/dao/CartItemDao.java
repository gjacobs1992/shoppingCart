package com.techelevator.dao;

import com.techelevator.model.CartItem;
import com.techelevator.model.Product;

import java.security.Principal;
import java.util.List;

public interface CartItemDao {
    List<CartItem> get(int userId);
    int add(int productId, int quantity, int userId);
    void updateProductQuantity(int productId, int newQuantity, int userId);
    void deleteItem(int productId, int userId);
    void clearCart(int userId);
}
