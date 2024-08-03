package com.pastebin.service.comment.controller;

import com.pastebin.service.comment.dto.CommentRequest;
import com.pastebin.service.comment.exception.CommentNotFoundException;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{pasteHash}")
    public ResponseEntity<Comment> createComment(@PathVariable String pasteHash,
                                                 @RequestBody CommentRequest request) {
        Comment comment = commentService.createComment(pasteHash, request.getContent(), request.getAuthor());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
        Comment comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id,
                                                 @RequestBody CommentRequest request) {
        Comment comment = commentService.updateComment(id, request.getContent());
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paste/{pasteHash}")
    public ResponseEntity<Page<Comment>> getCommentsForPaste(@PathVariable String pasteHash,
                                                             Pageable pageable) {
        Page<Comment> comments = commentService.getCommentsForPaste(pasteHash, pageable);
        return ResponseEntity.ok(comments);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFound(CommentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}