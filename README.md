# Stock API - Spring Boot & Firebase

## Overview

Stock API is a RESTful API built with **Spring Boot** and **Firebase** as the database. The API provides stock-related data, including retrieving stock prices, company information, and historical data.

## Features

- Retrieve stock data by name
- Fetch stock price by a specific date
- Retrieve stock prices within a date range
- Get stock summary statistics
- Compare multiple stocks over a period
- Retrieve a list of available stocks

## Technologies Used

- **Spring Boot** - Backend framework
- **Firebase** -  Database
- **Java** - Programming language
- **Gradle** - Build tool

The server will start on `http://localhost:8080`.

## API Endpoints

### 1. Get Stock Data

**GET** `/stock?name={stock_name}`

**Response:**

```json
{
"companyName": "TSLA",
    "name": "Tesla Inc",
    "logo": "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/TSLA.png",
    "prices": [
        {
            "date": "2024-10-22",
            "closePrice": 217.97,
            "openPrice": 217.31,
            "highPrice": 218.22,
            "lowPrice": 215.25999,
            "dailyChange": null,
            "dailyChangePercent": null,
            "volume": 43268700
        },
]
```

### 2. Get Stock Price by Date

**GET** `/stockByDate?name={stock_name}&date={yyyy-MM-dd}`

**Response:**

```json
{
 "date": "2025-01-02",
    "closePrice": 379.28,
    "openPrice": 390.10001,
    "highPrice": 392.73001,
    "lowPrice": 373.040009,
    "dailyChange": -24.56,
    "dailyChangePercent": -6.08,
    "volume": 109710700
}
```

### 3. Get Stock Prices by Date Range

**GET** `/stockByDateRange?name={stock_name}&startDate={yyyy-MM-dd}&endDate={yyyy-MM-dd}`

**Response:**

```json
[ {
        "date": "2025-01-02",
        "closePrice": 379.28,
        "openPrice": 390.10001,
        "highPrice": 392.73001,
        "lowPrice": 373.040009,
        "dailyChange": -24.56,
        "dailyChangePercent": -6.08,
        "volume": 109710700
    },
    {
        "date": "2025-01-03",
        "closePrice": 410.44,
        "openPrice": 381.48001,
        "highPrice": 411.88,
        "lowPrice": 379.45001,
        "dailyChange": 31.16,
        "dailyChangePercent": 8.22,
        "volume": 95423300
    },
    {
        "date": "2025-01-06",
        "closePrice": 411.049988,
        "openPrice": 423.20001,
        "highPrice": 426.42999,
        "lowPrice": 401.70001,
        "dailyChange": 0.61,
        "dailyChangePercent": 0.15,
        "volume": 85516500
    },

]
```

### 4. Get Stock Summary

**GET** `/stockSummary?name={stock_name}&startDate={yyyy-MM-dd}&endDate={yyyy-MM-dd}`

**Response:**

```json
{
    "totalVolume": 2371074300,
    "averageOpenPrice": 140.42,
    "percentageChange": -2.69,
    "minPrice": 133.83,
    "averageClosePrice": 139.75,
    "maxPrice": 153.13
}
```

### 5. Compare Stocks

**GET** `/compareStocks?names={stock1,stock2}&startDate={yyyy-MM-dd}&endDate={yyyy-MM-dd}`

**Response:**

```json
[
{
        "totalVolume": 501676100,
        "averageOpenPrice": 397.49,
        "percentageChange": 4.08,
        "minPrice": 373.04,
        "name": "TSLA",
        "averageClosePrice": 397.47,
        "maxPrice": 426.43
    },
    {
        "totalVolume": 1479681700,
        "averageOpenPrice": 142.94,
        "percentageChange": -1.74,
        "minPrice": 134.22,
        "name": "NVDA",
        "averageClosePrice": 141.39,
        "maxPrice": 153.13
    }
]
```

### 6. Get All Stock Names

**GET** `/stocks`

**Response:**

```json
[
    "AAPL",
    "AMD",
    "AMZN",
    "GOOGL",
    "META",
    "MSFT",
    "NVDA",
    "PLTR",
    "QQQ",
    "SPY",
    "TSLA"
]
```


