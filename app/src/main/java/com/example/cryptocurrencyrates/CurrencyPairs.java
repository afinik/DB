package com.example.cryptocurrencyrates;

public enum CurrencyPairs {
    ETHADX ("ETH-ADX"),
    USDTOMG ("USDT-OMG"),
    BTCDGB ("BTC-DGB"),
    USDUSDS ("USD-USDS");
    private String code;
    CurrencyPairs(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
