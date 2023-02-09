package com.techelevator.model;

public class WishListItem {
    private int wishListItemId;
    private int wishListId;
    private int productId;

    public WishListItem(int wishListItemId, int wishListId, int productId) {
        this.wishListItemId = wishListItemId;
        this.wishListId = wishListId;
        this.productId = productId;
    }

    public WishListItem() {
    }

    public int getWishListItemId() {
        return wishListItemId;
    }

    public void setWishListItemId(int wishListItemId) {
        this.wishListItemId = wishListItemId;
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
