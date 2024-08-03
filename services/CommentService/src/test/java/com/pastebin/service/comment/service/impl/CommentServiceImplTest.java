package com.pastebin.service.comment.service.impl;

import com.pastebin.service.comment.exception.CommentNotFoundException;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComment_ShouldReturnCreatedComment() {
        String pasteHash = "testHash";
        String content = "Test content";
        String author = "Test author";
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPasteHash(pasteHash);
        comment.setContent(content);
        comment.setAuthor(author);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment result = commentService.createComment(pasteHash, content, author);

        assertNotNull(result);
        assertEquals(pasteHash, result.getPasteHash());
        assertEquals(content, result.getContent());
        assertEquals(author, result.getAuthor());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void getComment_ShouldReturnComment_WhenCommentExists() {
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Comment result = commentService.getComment(commentId);

        assertNotNull(result);
        assertEquals(commentId, result.getId());
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void getComment_ShouldThrowException_WhenCommentDoesNotExist() {
        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> commentService.getComment(commentId));
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void updateComment_ShouldReturnUpdatedComment_WhenCommentExists() {
        Long commentId = 1L;
        String newContent = "Updated content";
        Comment existingComment = new Comment();
        existingComment.setId(commentId);
        existingComment.setContent("Old content");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(existingComment);

        Comment result = commentService.updateComment(commentId, newContent);

        assertNotNull(result);
        assertEquals(commentId, result.getId());
        assertEquals(newContent, result.getContent());
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void deleteComment_ShouldDeleteComment() {
        Long commentId = 1L;

        commentService.deleteComment(commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void getCommentsForPaste_ShouldReturnPageOfComments() {
        String pasteHash = "testHash";
        Pageable pageable = Pageable.unpaged();
        Page<Comment> commentPage = new PageImpl<>(Collections.singletonList(new Comment()));

        when(commentRepository.findByPasteHash(pasteHash, pageable)).thenReturn(commentPage);

        Page<Comment> result = commentService.getCommentsForPaste(pasteHash, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(commentRepository, times(1)).findByPasteHash(pasteHash, pageable);
    }
}