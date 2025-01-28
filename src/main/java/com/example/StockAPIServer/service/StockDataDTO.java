package com.example.StockAPIServer.service;

import java.util.List;
import java.util.Map;

public class StockDataDTO {
    private String companyName;
    private String name;
    private String logo;
    private List<StockPrice> prices;

    // Constructors
    public StockDataDTO(String companyName,String name, String logo, List<StockPrice> prices) {
        this.companyName = companyName;
        this.name = name;
        this.logo = logo;
        this.prices = prices;
    }

    // Getters and Setters

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public List<StockPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<StockPrice> prices) {
        this.prices = prices;
    }
}
