package com.example.StockAPIServer.controller;

import com.example.StockAPIServer.service.StockDataDTO;
import com.example.StockAPIServer.service.StockPrice;
import com.example.StockAPIServer.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock")
    public StockDataDTO getStock(@RequestParam String name) {
        return stockService.getStockData(name);
    }

    @GetMapping("/stockByDate")
    public StockPrice getStockPriceByDate(@RequestParam String name, @RequestParam String date) {
        return stockService.getStockPriceByDate(name, date);
    }

    @GetMapping("/stockByDateRange")
    public List<StockPrice> getStockPricesByDateRange(
            @RequestParam String name,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return stockService.getStockPricesByDateRange(name, startDate, endDate);
    }

    @GetMapping("/stockSummary")
    public Map<String, Object> getStockSummary(
            @RequestParam String name,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return stockService.getStockSummary(name, startDate, endDate);
    }
    @GetMapping("/compareStocks")
    public List<Map<String, Object>> compareStocks(
            @RequestParam List<String> names,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return stockService.compareStocks(names, startDate, endDate);
    }


}
