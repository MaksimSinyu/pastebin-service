package com.pastebin.service.comment.service.impl;

import com.pastebin.service.comment.exception.CommentNotFoundException;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.repository.CommentRepository;
import com.pastebin.service.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(String pasteHash, String content, String author) {
        Comment comment = Comment.builder()
                .content(content)
                .pasteHash(pasteHash)
                .createdAt(DateTime.now())
                .author(author)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found: " + id));
    }

    @Override
    public Comment updateComment(Long id, String content) {
        Comment comment = getComment(id);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> getCommentsForPaste(String pasteHash, Pageable pageable) {
        return commentRepository.findByPasteHash(pasteHash, pageable);
    }
}