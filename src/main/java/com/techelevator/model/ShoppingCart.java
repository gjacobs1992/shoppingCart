package com.techelevator.model;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCart {

    private List<CartItem> items;
    private BigDecimal subtotal;
    private double taxRate;
    private BigDecimal total;

    public ShoppingCart(List<CartItem> items, BigDecimal subtotal, double taxRate, BigDecimal total) {
        this.items = items;
        this.subtotal = subtotal;
        this.taxRate = taxRate;
        this.total = total;
    }

    public ShoppingCart() {
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
