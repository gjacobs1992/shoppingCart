package com.techelevator.services;

import com.techelevator.dao.JdbcWishListItemDao;
import com.techelevator.dao.UserDao;
import com.techelevator.dao.WishListDao;
import com.techelevator.dao.WishListItemDao;
import com.techelevator.model.User;
import com.techelevator.model.WishList;
import com.techelevator.model.WishListItem;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class WishListService {
    private WishListItemDao wishListItemDao;
    private WishListDao wishListDao;
    private UserDao userDao;

    public WishListService(WishListItemDao wishListItemDao, WishListDao wishListDao, UserDao userDao) {
        this.wishListItemDao = wishListItemDao;
        this.wishListDao = wishListDao;
        this.userDao = userDao;
    }
    public WishList createNewWishlist(Principal principal, WishList wishList){
        WishList newList = new WishList();
        User user = findPrincipalUser(principal);
        int newId = wishListDao.createWishList(user.getId(), wishList.getName());
        newList = buildWishList(principal, newId);
        return newList;
    }

    public WishList buildWishList(Principal principal, int wishListId){
        WishList newList = wishListDao.getOneWishList(wishListId);
        User user = findPrincipalUser(principal);
        List<WishListItem> itemList = wishListItemDao.getAllItems(user.getId(), wishListId);
        newList.setList(itemList);
        newList.setName(newList.getName());
        return newList;
    }
    public void deleteWishlist(Principal principal, int wishListId){
        User user = findPrincipalUser(principal);
        wishListDao.deleteWishList(user.getId(), wishListId);
    }
    private User findPrincipalUser(Principal principal){
        String username = principal.getName();
        return userDao.findByUsername(username);
    }
}
