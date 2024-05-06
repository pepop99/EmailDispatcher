package com.pepop99.emaildispatcher.metadata;

import com.pepop99.emaildispatcher.exceptions.DuplicateEmailException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Singleton class
public class AppMeta {
    public static final AppMeta instance = new AppMeta();

    // should not be re-initialised
    private AppMeta() {
    }

    // different jetty threads manipulate this set, hence should be volatile
    // can also use a Hashmap of email and BaseMeta (if we want to modify some meta entry), instead of a set.
    public volatile Set<BaseMeta> meta = new HashSet<>();
    public volatile Map<String, ArrayList<String>> emailMap = new HashMap<>();
    public volatile ArrayList<GrantMeta> grantsArrayList = new ArrayList<>();

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

    public void getAllNonProfits(ArrayList<NonProfitMeta> list) {
        meta.forEach(baseMeta -> {
            if (baseMeta instanceof NonProfitMeta) {
                list.add((NonProfitMeta) baseMeta);
            }
        });
    }

    public void getAllFoundations(ArrayList<FoundationMeta> list) {
        meta.forEach(baseMeta -> {
            if (baseMeta instanceof FoundationMeta) {
                list.add((FoundationMeta) baseMeta);
            }
        });
    }
}
