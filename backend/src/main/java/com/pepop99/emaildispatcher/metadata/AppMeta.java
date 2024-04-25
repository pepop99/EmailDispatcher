package com.pepop99.emaildispatcher.metadata;

import com.pepop99.emaildispatcher.exceptions.DuplicateEmailException;

import java.util.HashSet;
import java.util.Set;

// Singleton class
public class AppMeta {
    // different jetty threads manipulate this set, hence should be volatile
    public static volatile AppMeta instance = new AppMeta();

    // should not be re-initialised
    private AppMeta() {
    }

    private final Set<BaseMeta> meta = new HashSet<>();

    public void insertNonProfitMeta(String email, String name, String address) throws DuplicateEmailException {
        validateEmail(email);
        meta.add(new NonProfitMeta(email, name, address));
    }

    public void insertFoundationMeta(String email) throws DuplicateEmailException {
        validateEmail(email);
        meta.add(new FoundationMeta(email));
    }

    public void validateEmail(String email) throws DuplicateEmailException {
        if (meta.contains(new BaseMeta(email))) {
            throw new DuplicateEmailException("Email already associated");
        }
    }
}
