package com.example.StockAPIServer.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final Firestore firestore;

    public StockService(Firestore firestore) {
        this.firestore = firestore;
    }

    public StockDataDTO getStockData(String stockName) {
        try {
            ApiFuture<DocumentSnapshot> future = firestore.collection("stocks").document(stockName).get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                String name = document.getString("name");
                String companyName = document.getString("companyName");
                String logo = document.getString("logo");

                List<Map<String, Object>> history = (List<Map<String, Object>>) document.get("history");
                List<StockPrice> prices = new ArrayList<>();

                if (history != null) {
                    for (Map<String, Object> dayData : history) {
                        StockPrice price = new StockPrice(
                                (String) dayData.get("date"),
                                dayData.get("closePrice") != null ? ((Number) dayData.get("closePrice")).doubleValue() : null,
                                dayData.get("openPrice") != null ? ((Number) dayData.get("openPrice")).doubleValue() : null,
                                dayData.get("highPrice") != null ? ((Number) dayData.get("highPrice")).doubleValue() : null,
                                dayData.get("lowPrice") != null ? ((Number) dayData.get("lowPrice")).doubleValue() : null,
                                dayData.get("dailyChange") != null ? ((Number) dayData.get("dailyChange")).doubleValue() : null,
                                dayData.get("dailyChangePercent") != null ? ((Number) dayData.get("dailyChangePercent")).doubleValue() : null,
                                dayData.get("volume") != null ? ((Number) dayData.get("volume")).longValue() : null
                        );
                        prices.add(price);
                    }
                }
                return new StockDataDTO(name, companyName, logo, prices);
            } else {
                throw new RuntimeException("Stock not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch stock data", e);
        }
    }
    public StockPrice getStockPriceByDate(String stockName, String date) {
        try {
            ApiFuture<DocumentSnapshot> future = firestore.collection("stocks").document(stockName).get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                List<Map<String, Object>> history = (List<Map<String, Object>>) document.get("history");

                if (history != null) {
                    for (Map<String, Object> dayData : history) {
                        if (dayData.get("date").equals(date)) {
                            return new StockPrice(
                                    (String) dayData.get("date"),
                                    dayData.get("closePrice") != null ? ((Number) dayData.get("closePrice")).doubleValue() : null,
                                    dayData.get("openPrice") != null ? ((Number) dayData.get("openPrice")).doubleValue() : null,
                                    dayData.get("highPrice") != null ? ((Number) dayData.get("highPrice")).doubleValue() : null,
                                    dayData.get("lowPrice") != null ? ((Number) dayData.get("lowPrice")).doubleValue() : null,
                                    dayData.get("dailyChange") != null ? ((Number) dayData.get("dailyChange")).doubleValue() : null,
                                    dayData.get("dailyChangePercent") != null ? ((Number) dayData.get("dailyChangePercent")).doubleValue() : null,
                                    dayData.get("volume") != null ? ((Number) dayData.get("volume")).longValue() : null
                            );
                        }
                    }
                }
                throw new RuntimeException("No data found for the given date!");
            } else {
                throw new RuntimeException("Stock not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch stock price by date", e);
        }
    }
    public List<StockPrice> getStockPricesByDateRange(String stockName, String startDate, String endDate) {
        try {
            ApiFuture<DocumentSnapshot> future = firestore.collection("stocks").document(stockName).get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                List<Map<String, Object>> history = (List<Map<String, Object>>) document.get("history");
                List<StockPrice> filteredPrices = new ArrayList<>();

                if (history != null) {
                    for (Map<String, Object> dayData : history) {
                        String date = (String) dayData.get("date");
                        if (date != null && isDateInRange(date, startDate, endDate)) {
                            filteredPrices.add(new StockPrice(
                                    date,
                                    dayData.get("closePrice") != null ? ((Number) dayData.get("closePrice")).doubleValue() : null,
                                    dayData.get("openPrice") != null ? ((Number) dayData.get("openPrice")).doubleValue() : null,
                                    dayData.get("highPrice") != null ? ((Number) dayData.get("highPrice")).doubleValue() : null,
                                    dayData.get("lowPrice") != null ? ((Number) dayData.get("lowPrice")).doubleValue() : null,
                                    dayData.get("dailyChange") != null ? ((Number) dayData.get("dailyChange")).doubleValue() : null,
                                    dayData.get("dailyChangePercent") != null ? ((Number) dayData.get("dailyChangePercent")).doubleValue() : null,
                                    dayData.get("volume") != null ? ((Number) dayData.get("volume")).longValue() : null
                            ));
                        }
                    }
                }
                if (filteredPrices.isEmpty()) {
                    throw new RuntimeException("No data found for the given date range!");
                }
                return filteredPrices;
            } else {
                throw new RuntimeException("Stock not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch stock prices by date range", e);
        }
    }

    private boolean isDateInRange(String date, String startDate, String endDate) {
        try {
            LocalDate targetDate = LocalDate.parse(date);
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            return (targetDate.isEqual(start) || targetDate.isAfter(start)) &&
                    (targetDate.isEqual(end) || targetDate.isBefore(end));
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format. Please use YYYY-MM-DD.", e);
        }
    }

    public Map<String, Object> getStockSummary(String stockName, String startDate, String endDate) {
        try {
            List<StockPrice> prices = getStockPricesByDateRange(stockName, startDate, endDate);

            double totalClosePrice = 0;
            double totalOpenPrice = 0;
            double maxPrice = Double.MIN_VALUE;
            double minPrice = Double.MAX_VALUE;
            long totalVolume = 0;

            for (StockPrice price : prices) {
                totalClosePrice += price.getClosePrice();
                totalOpenPrice += price.getOpenPrice();
                maxPrice = Math.max(maxPrice, price.getHighPrice());
                minPrice = Math.min(minPrice, price.getLowPrice());
                totalVolume += price.getVolume();
            }

            int count = prices.size();
            double averageClosePrice = round(totalClosePrice / count, 2);
            double averageOpenPrice = round(totalOpenPrice / count, 2);
            double percentageChange = round(((prices.get(prices.size() - 1).getClosePrice() - prices.get(0).getClosePrice()) / prices.get(0).getClosePrice()) * 100, 2);
            maxPrice = round(maxPrice, 2);
            minPrice = round(minPrice, 2);


            Map<String, Object> summary = new HashMap<>();
            summary.put("averageClosePrice", averageClosePrice);
            summary.put("averageOpenPrice", averageOpenPrice);
            summary.put("maxPrice", maxPrice);
            summary.put("minPrice", minPrice);
            summary.put("totalVolume", totalVolume);
            summary.put("percentageChange", percentageChange);

            return summary;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate stock summary", e);
        }
    }


    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public List<Map<String, Object>> compareStocks(List<String> stockNames, String startDate, String endDate) {
        List<Map<String, Object>> comparison = new ArrayList<>();

        for (String stockName : stockNames) {
            try {
                Map<String, Object> summary = getStockSummary(stockName, startDate, endDate);
                summary.put("name", stockName);
                comparison.add(summary);
            } catch (Exception e) {
                System.err.println("Failed to fetch data for stock: " + stockName);
            }
        }

        return comparison;
    }

    public List<String> getAllStockNames() {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("stocks").get();
            QuerySnapshot documents = future.get();

            List<String> stockNames = new ArrayList<>();

            for (DocumentSnapshot document : documents.getDocuments()) {
                String name = document.getString("name");
                if (name != null) {
                    stockNames.add(name);
                }
            }
            return stockNames;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch stock names", e);
        }
    }


}

