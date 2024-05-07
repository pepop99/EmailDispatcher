package com.pepop99.emaildispatcher.metadata;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class GrantMeta {
    private GrantType grantType;
    private String[] tags;
    private final HashMap<String, String> otherData = new HashMap<>();
}

