package com.pastebin.service.comment.repository;

import com.pastebin.service.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPasteHashOrderByCreatedAtDesc(String pasteHash);
}