package com.techelevator.dao;

import com.techelevator.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT product_id, product_sku, name, price FROM product;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        return buildList(results);
    }

    @Override
    public List<Product> getProductByNameOrSku(String name, String sku) {
        String sql = "SELECT product_id, product_sku, name, price  " +
                "FROM product " +
                "WHERE name ILIKE ? OR product_sku ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%"+name+"%", "%"+sku+"%");
        return buildList(results);
    }
    @Override
    public Product getProductById(int id) {
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product " +
                "WHERE product_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            return mapRowToProductAllFields(results);
        }
        return null;
    }
    private Product mapRowsToProductNoDetails(SqlRowSet rowSet){
        Product product = new Product();
        product.setId(rowSet.getInt("product_id"));
        product.setSku(rowSet.getString("product_sku"));
        product.setName(rowSet.getString("name"));
        product.setPrice(rowSet.getBigDecimal("price"));
        return product;
    }
    private Product mapRowToProductAllFields(SqlRowSet rowSet){
        Product product = new Product();
        product.setId(rowSet.getInt("product_id"));
        product.setSku(rowSet.getString("product_sku"));
        product.setName(rowSet.getString("name"));
        product.setDescription(rowSet.getString("description"));
        product.setPrice(rowSet.getBigDecimal("price"));
        product.setImageName(rowSet.getString("image_name"));
        return product;
    }
    private List<Product> buildList(SqlRowSet rs){
        List<Product> list = new ArrayList<>();
        while (rs.next()){
            Product product = mapRowsToProductNoDetails(rs);
            list.add(product);
        }
        return list;
    }
}
