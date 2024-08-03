package com.pastebin.service.comment.service.impl;

import com.pastebin.service.comment.dto.CommentRequest;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.repository.CommentRepository;
import com.pastebin.service.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(String pasteHash, CommentRequest request) {
        Comment comment = Comment.builder()
                .pasteHash(pasteHash)
                .content(request.getContent())
                .author(request.getAuthor())
                .createdAt(LocalDateTime.now())
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPasteHash(String pasteHash) {
        return commentRepository.findByPasteHashOrderByCreatedAtDesc(pasteHash);
    }
}