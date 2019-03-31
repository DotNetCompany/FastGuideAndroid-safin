package com.fast.guide.Connection;

public class HttpParamiter {
    private String Key;
    private String Value;


    public HttpParamiter(String key, String value) {
        this.Key = key;
        this.Value = value;
    }


    public String getKey() {
        return Key;
    }

    public String getValue() {
        return Value;
    }
}
