package com.pepop99.emaildispatcher.metadata;

public class NonProfitMeta extends BaseMeta {
    private final String name;
    private final String address;

    public NonProfitMeta(String email, String name, String address) {
        super(email);
        this.name = name;
        this.address = address;
    }
}
