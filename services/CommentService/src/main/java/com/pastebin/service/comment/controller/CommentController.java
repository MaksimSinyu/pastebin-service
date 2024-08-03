package com.pastebin.service.comment.controller;

import com.pastebin.service.comment.dto.CommentRequest;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{pasteHash}")
    public ResponseEntity<Comment> createComment(@PathVariable String pasteHash,
                                                 @RequestBody CommentRequest request) {
        Comment comment = commentService.createComment(pasteHash, request);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/paste/{pasteHash}")
    public ResponseEntity<List<Comment>> getCommentsByPasteHash(@PathVariable String pasteHash) {
        List<Comment> comments = commentService.getCommentsByPasteHash(pasteHash);
        return ResponseEntity.ok(comments);
    }
}