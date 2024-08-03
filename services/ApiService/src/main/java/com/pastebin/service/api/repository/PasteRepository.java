package com.pastebin.service.api.repository;

import com.pastebin.service.api.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PasteRepository extends JpaRepository<Paste, String>,
        JpaSpecificationExecutor<Paste>,
        QuerydslPredicateExecutor<Paste> {
}