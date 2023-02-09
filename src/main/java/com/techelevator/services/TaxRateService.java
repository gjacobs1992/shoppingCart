package com.techelevator.services;

import com.techelevator.model.TaxRateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

@Component
public class TaxRateService {
    public static String API_BASE_URL = "https://teapi.netlify.app/api/statetax";
    private RestTemplate restTemplate = new RestTemplate();

    public TaxRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TaxRateService() {
    }

    public BigDecimal getTaxRate(String state){
        ResponseEntity<TaxRateResponse> response = restTemplate.getForEntity(API_BASE_URL + "?state=" + state, TaxRateResponse.class);
        BigDecimal taxRate = response.getBody().getTaxRate();
        return taxRate.divide(new BigDecimal(100));
    }
}
