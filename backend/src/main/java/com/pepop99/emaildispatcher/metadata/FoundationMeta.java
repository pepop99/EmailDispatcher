package com.pepop99.emaildispatcher.metadata;

import org.json.JSONObject;

public class FoundationMeta extends BaseMeta {
    public FoundationMeta(String email) {
        super(email);
    }

    @Override
    public String toString() {
        final JSONObject json = new JSONObject();
        json.put("email", getEmail());
        return json.toString();
    }
}
