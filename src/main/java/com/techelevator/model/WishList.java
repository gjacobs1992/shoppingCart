package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishList {
    private int wishListId;
    private int userId;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<WishListItem> list;

    public WishList(int wishListId, int userId, String name, List<WishListItem> list) {
        this.wishListId = wishListId;
        this.userId = userId;
        this.name = name;
        this.list = list;
    }

    public WishList() {
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WishListItem> getList() {
        return list;
    }

    public void setList(List<WishListItem> list) {
        this.list = list;
    }
}
