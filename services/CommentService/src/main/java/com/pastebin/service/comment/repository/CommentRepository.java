package com.pastebin.service.comment.repository;

import com.pastebin.service.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPasteHash(String pasteHash, Pageable pageable);
}