package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    List<Product> getProductByNameOrSku(String name, String sku);
    Product getProductById(int id);
}
