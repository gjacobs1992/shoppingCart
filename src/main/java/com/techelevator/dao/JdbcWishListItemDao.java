package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcWishListItemDao implements WishListItemDao {
    private UserDao userDao;
    private JdbcTemplate jdbcTemplate;

    public JdbcWishListItemDao(UserDao userDao, JdbcTemplate jdbcTemplate) {
        this.userDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WishListItem> getAllItems(int userId, int wishListId) {
        List<WishListItem> list = new ArrayList<>();
        String sql = "SELECT wishlist_item.wishlist_item_id, wishlist_item.wishlist_id, wishlist_item.product_id " +
                "FROM wishlist_item " +
                "JOIN wishlist ON wishlist.wishlist_id = wishlist_item.wishlist_id " +
                "WHERE wishlist.user_id = ? AND wishlist_item.wishlist_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, wishListId);
        while (results.next()){
            list.add(mapRowToWishListItem(results));
        }
        return list;
    }

    @Override
    public int addItem(int productId, int quantity, int userId) {
        return 0;
    }

    @Override
    public void deleteItem(int productId, int userId) {

    }

    private WishListItem mapRowToWishListItem(SqlRowSet rowSet){
        WishListItem wishlistItem = new WishListItem();
        wishlistItem.setWishListItemId(rowSet.getInt("wishlist_item_id"));
        wishlistItem.setWishListId(rowSet.getInt("wishlist_id"));
        wishlistItem.setProductId(rowSet.getInt("product_id"));
        return wishlistItem;
    }
    private User findPrincipalUser(Principal principal){
        String username = principal.getName();
        return userDao.findByUsername(username);
    }
}
