package com.pepop99.emaildispatcher.metadata;

import org.json.JSONObject;

public class NonProfitMeta extends BaseMeta {
    private final String name;
    private final String address;

    public NonProfitMeta(String email, String name, String address) {
        super(email);
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        final JSONObject json = new JSONObject();
        json.put("email", getEmail());
        json.put("name", name);
        json.put("address", address);
        return json.toString();
    }
}
