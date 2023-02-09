package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TaxRateResponse {
    @JsonProperty("salesTax")
    private BigDecimal taxRate;
    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
