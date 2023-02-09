package com.techelevator.dao;

import com.techelevator.model.WishListItem;
import com.techelevator.model.WishList;

import java.security.Principal;
import java.util.List;

public interface WishListItemDao {
    List<WishListItem> getAllItems(int userId, int wishListId);
    int addItem(int productId, int quantity, int userId);
    void deleteItem(int productId, int userId);
}
