package com.techelevator.services;

import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import com.techelevator.model.ShoppingCart;
import com.techelevator.model.User;
import com.techelevator.services.TaxRateService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;

@Component
public class ShoppingCartService {
    private CartItemDao cartItemDao;
    private TaxRateService taxRateService;
    private ProductDao productDao;
    private UserDao userDao;

    public ShoppingCartService(CartItemDao cartItemDao, TaxRateService taxRateService, ProductDao productDao, UserDao userDao){
        this.cartItemDao = cartItemDao;
        this.taxRateService = taxRateService;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    public BigDecimal buildProductSubtotal(CartItem item){
        Product product = productDao.getProductById(item.getProductId());
        BigDecimal price = product.getPrice();
        return price.multiply(BigDecimal.valueOf(item.getQuantity()));
    }

    public BigDecimal buildCartSubtotal(List<CartItem> items){
        BigDecimal subtotal = BigDecimal.ZERO;
        for(CartItem item : items){
            subtotal = subtotal.add(buildProductSubtotal(item));
        }
        return subtotal;
    }

    public ShoppingCart buildCart(Principal principal){
        ShoppingCart cart = new ShoppingCart();
        User user = findPrincipalUser(principal);

        BigDecimal subtotal = buildCartSubtotal(cartItemDao.get(user.getId()));
        double taxRate = taxRateService.getTaxRate(user.getStateCode()).multiply(new BigDecimal(100)).doubleValue();
        BigDecimal total = subtotal.add(subtotal.multiply(taxRateService.getTaxRate(user.getStateCode())));
        total = total.setScale(2, RoundingMode.HALF_UP);

        cart.setItems(cartItemDao.get(user.getId()));
        cart.setSubtotal(subtotal);
        cart.setTotal(total);
        cart.setTaxRate(taxRate);
        return cart;
    }

    public ShoppingCart addOrUpdateItem(CartItem cartItem, Principal principal){
        ShoppingCart cart = new ShoppingCart();
        User user = findPrincipalUser(principal);
        cartItem.setUserId(user.getId());

        if(!itemExists(cartItem, principal)){
            cartItemDao.add(cartItem.getProductId(), cartItem.getQuantity(), user.getId());
            cart = buildCart(principal);
            return cart;
        }else{
            cartItemDao.updateProductQuantity(cartItem.getProductId(), cartItem.getQuantity(), user.getId());
            cart = buildCart(principal);
            return cart;
        }
    }
    private boolean itemExists(CartItem cartItem, Principal principal){
        boolean exists = false;
        User user = findPrincipalUser(principal);
        List<CartItem> items = cartItemDao.get(user.getId());
        int incomingProductId = cartItem.getProductId();
        for(CartItem item : items){
            if (item.getProductId() == incomingProductId) {
                exists = true;
                break;
            }
        }
        return exists;
    }
    public ShoppingCart deleteItem(int id, Principal principal){
        ShoppingCart cart = new ShoppingCart();
        User user = findPrincipalUser(principal);
        cartItemDao.deleteItem(id, user.getId());
        cart = buildCart(principal);
        return cart;
    }

    public ShoppingCart clearCart(Principal principal){
        ShoppingCart cart = buildCart(principal);
        User user = findPrincipalUser(principal);
        cartItemDao.clearCart(user.getId());
        return cart;
    }

    private User findPrincipalUser(Principal principal){
        String username = principal.getName();
        return userDao.findByUsername(username);
    }


}
