package com.techelevator.controller;

import com.techelevator.dao.JdbcProductDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int id){
        return productDao.getProductById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getProductsByNameOrSku(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String sku){
        if(name == null && sku == null){
            return productDao.getAll();
        }
        return productDao.getProductByNameOrSku(name, sku);
    }
}
