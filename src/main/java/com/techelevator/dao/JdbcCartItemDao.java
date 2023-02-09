package com.techelevator.dao;

import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCartItemDao implements CartItemDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcCartItemDao() {
    }

    @Override
    public List<CartItem> get(int userId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT cart_item_id, cart_item.user_id, product_id, quantity " +
                "FROM cart_item " +
                "JOIN users ON users.user_id = cart_item.user_id "+
                "WHERE cart_item.user_id = ?;";
        SqlRowSet results =jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()){
            items.add(mapRowToCartItem(results));
        }
        return items;
    }

    @Override
    public int add(int productId, int quantity, int userId) {
        String sql = "INSERT INTO cart_item (user_id, product_id, quantity)" +
                "VALUES(?,?,?)" +
                "RETURNING cart_item_id;";
        int cartItemId = jdbcTemplate.queryForObject(sql, int.class, userId, productId, quantity);
        return cartItemId;
    }

    @Override
    public void updateProductQuantity(int productId, int newQuantity, int userId) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE product_id = ? AND user_id = ?;";
        jdbcTemplate.update(sql, newQuantity, productId, userId);
    }

    @Override
    public void deleteItem(int productId, int userId) {
        String sql = "DELETE FROM cart_item WHERE product_id = ? AND user_id = ?;";
        jdbcTemplate.update(sql, productId, userId);
    }

    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ?;";
        jdbcTemplate.update(sql, userId);
    }

    private CartItem mapRowToCartItem(SqlRowSet rowSet){
        CartItem cartItem = new CartItem();
        cartItem.setId(rowSet.getInt("cart_item_id"));
        cartItem.setUserId(rowSet.getInt("user_id"));
        cartItem.setProductId(rowSet.getInt("product_id"));
        cartItem.setQuantity(rowSet.getInt("quantity"));
        return cartItem;
    }
}
