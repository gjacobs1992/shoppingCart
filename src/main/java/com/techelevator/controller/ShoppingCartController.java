package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.CartItem;
import com.techelevator.model.ShoppingCart;
import com.techelevator.model.User;
import com.techelevator.services.ShoppingCartService;
import com.techelevator.services.TaxRateService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;


@RestController
@RequestMapping(path = "/cart")
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController {
    private CartItemDao cartItemDao;
    private TaxRateService taxRateService;
    private ShoppingCartService shoppingCartService;
    private UserDao userDao;

    public ShoppingCartController(CartItemDao cartItemDao, TaxRateService taxRateService, ShoppingCartService shoppingCartService, UserDao userDao){
        this.cartItemDao = cartItemDao;
        this.taxRateService = taxRateService;
        this.shoppingCartService = shoppingCartService;
        this.userDao = userDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ShoppingCart getCart(Principal principal){
        return shoppingCartService.buildCart(principal);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/items", method = RequestMethod.POST)
    public ShoppingCart addOrUpdateItem(@RequestBody CartItem cartItem, Principal principal){
        User user = findPrincipalUser(principal);
        if(cartItem.getUserId() > 0 ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if(cartItem.getQuantity() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be a positive number.");
        }
        return shoppingCartService.addOrUpdateItem(cartItem, principal);
    }


    @RequestMapping(path = "/items/{id}", method = RequestMethod.DELETE)
    public ShoppingCart removeItem(@PathVariable int id, Principal principal){
        ShoppingCart cart = shoppingCartService.buildCart(principal);
        if(cart.getItems().size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty.");
        }
        cart = shoppingCartService.deleteItem(id, principal);
        return cart;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE)
    public ShoppingCart clearCart(Principal principal){
        ShoppingCart cart = shoppingCartService.buildCart(principal);
        if(cart.getItems().size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty.");
        }
        cart = shoppingCartService.clearCart(principal);
        return cart;
    }

    @RequestMapping(path = "/whoami", method = RequestMethod.GET)
    public String whoAmI(Principal principal){
        return principal.getName();
    }

    @RequestMapping(path = "/taxrate", method = RequestMethod.GET)
    public BigDecimal getTaxRate(@RequestParam String state){
        TaxRateService tax = new TaxRateService();
        return tax.getTaxRate(state);
    }
    private User findPrincipalUser(Principal principal){
        String username = principal.getName();
        return userDao.findByUsername(username);
    }
}
