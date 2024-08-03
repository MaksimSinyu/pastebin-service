package com.pastebin.service.comment.service;

import com.pastebin.service.comment.dto.CommentRequest;
import com.pastebin.service.comment.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(String pasteHash, CommentRequest request);
    List<Comment> getCommentsByPasteHash(String pasteHash);
}