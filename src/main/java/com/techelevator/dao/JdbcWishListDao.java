package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.WishList;
import com.techelevator.model.WishListItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcWishListDao implements WishListDao{
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    public JdbcWishListDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<WishList> listAllWishLists(Principal principal) {
        List<WishList> lists = new ArrayList<>();
        User user = userDao.findByUsername(principal.getName());
        String sql = "SELECT wishlist_id, user_id, name " +
                "FROM wishlist WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
        while (results.next()){
            lists.add(mapRowToWishList(results));
        }
        return lists;
    }

    @Override
    public WishList getOneWishList(int wishListId) {
        WishList wishList = new WishList();
        String sql = "SELECT wishlist_id, user_id, name " +
                "FROM wishlist " +
                "WHERE wishlist_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, wishListId);
        if(results.next()){
            wishList = mapRowToWishList(results);
        }
        return wishList;
    }

    @Override
    public int createWishList(int userId, String name) {
        String sql = "INSERT INTO wishlist (user_id, name) " +
                "VALUES (?,?) " +
                "RETURNING wishlist_id;";
        return jdbcTemplate.queryForObject(sql, int.class, userId, name);
    }

    @Override
    public void deleteWishList(int userId, int wishlistId) {
        String sql = "DELETE FROM wishlist WHERE user_id = ? AND wishlist_id = ?;";
        jdbcTemplate.update(sql,userId,wishlistId);
    }
    private WishList mapRowToWishList(SqlRowSet rowSet){
        WishList wishlist = new WishList();
        wishlist.setWishListId(rowSet.getInt("wishlist_id"));
        wishlist.setUserId(rowSet.getInt("user_id"));
        wishlist.setName(rowSet.getString("name"));
        return wishlist;
    }
    private User findPrincipalUser(Principal principal){
        String username = principal.getName();
        return userDao.findByUsername(username);
    }
}
