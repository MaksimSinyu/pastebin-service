package com.pastebin.service.comment.service;

import com.pastebin.service.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment createComment(String pasteHash, String content, String author);
    Comment getComment(Long id);
    Comment updateComment(Long id, String content);
    void deleteComment(Long id);
    Page<Comment> getCommentsForPaste(String pasteHash, Pageable pageable);
}