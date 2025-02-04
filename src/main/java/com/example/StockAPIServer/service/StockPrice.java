package com.example.StockAPIServer.service;

public class StockPrice {
    private String date;
    private Double closePrice;
    private Double openPrice;
    private Double highPrice;
    private Double lowPrice;
    private Double dailyChange;
    private Double dailyChangePercent;
    private Long volume;


    public StockPrice(String date, Double closePrice, Double openPrice, Double highPrice, Double lowPrice, Double dailyChange, Double dailyChangePercent, Long volume) {
        this.date = date;
        this.closePrice = closePrice;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.dailyChange = dailyChange;
        this.dailyChangePercent = dailyChangePercent;
        this.volume = volume;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getDailyChange() {
        return dailyChange;
    }

    public void setDailyChange(Double dailyChange) {
        this.dailyChange = dailyChange;
    }

    public Double getDailyChangePercent() {
        return dailyChangePercent;
    }

    public void setDailyChangePercent(Double dailyChangePercent) {
        this.dailyChangePercent = dailyChangePercent;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}

