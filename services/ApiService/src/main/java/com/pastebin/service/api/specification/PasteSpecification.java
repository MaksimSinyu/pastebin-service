package com.pastebin.service.api.specification;

import com.pastebin.service.api.model.Paste;
import org.springframework.data.jpa.domain.Specification;

import org.joda.time.DateTime;

public class PasteSpecification {

    public static Specification<Paste> hasLanguage(String language) {
        return (root, query, cb) -> {
            if (language == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.equal(root.get("language"), language);
        };
    }

    public static Specification<Paste> isPublic(Boolean isPublic) {
        return (root, query, cb) -> {
            if (isPublic == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.equal(root.get("isPublic"), isPublic);
        };
    }

    public static Specification<Paste> createdAfter(DateTime date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.greaterThan(root.get("createdAt"), date);
        };
    }

    public static Specification<Paste> titleContains(String title) {
        return (root, query, cb) -> {
            if (title == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }
}