package com.techelevator.controller;

import com.techelevator.dao.JdbcWishListDao;
import com.techelevator.model.WishList;
import com.techelevator.services.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/wishlists")
@PreAuthorize("isAuthenticated()")
public class WishListController {
    private JdbcWishListDao jdbcWishListDao;
    private WishListService wishListService;

    public WishListController(JdbcWishListDao jdbcWishListDao, WishListService wishListService) {
        this.jdbcWishListDao = jdbcWishListDao;
        this.wishListService = wishListService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<WishList> getWishLists(Principal principal){
        return jdbcWishListDao.listAllWishLists(principal);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public WishList getWishListById(Principal principal, @PathVariable int id){
        return wishListService.buildWishList(principal, id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public WishList createWishList(Principal principal, @RequestBody WishList wishList){
        return wishListService.createNewWishlist(principal, wishList);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteWishlistById(Principal principal, @PathVariable int id){
        wishListService.deleteWishlist(principal, id);
    }
}
