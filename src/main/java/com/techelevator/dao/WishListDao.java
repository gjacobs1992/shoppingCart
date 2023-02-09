package com.techelevator.dao;

import com.techelevator.model.WishList;

import java.security.Principal;
import java.util.List;

public interface WishListDao {
    List<WishList> listAllWishLists(Principal principal);
    WishList getOneWishList(int wishListId);
    int createWishList(int userId, String name );
    void deleteWishList(int userId, int wishlistId);
}
