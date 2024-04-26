package com.pepop99.emaildispatcher.metadata;

import lombok.Getter;

import java.util.Objects;

@Getter
public class BaseMeta {
    private final String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseMeta baseMeta)) return false;
        return Objects.equals(email, baseMeta.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public BaseMeta(String email) {
        this.email = email;
    }
}
