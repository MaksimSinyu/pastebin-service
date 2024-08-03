package com.pastebin.service.comment.service.impl;

import com.pastebin.service.comment.dto.CommentRequest;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
        String pasteHash = "testPasteHash";
        CommentRequest request = new CommentRequest();
        request.setContent("Test content");
        request.setAuthor("Test author");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPasteHash(pasteHash);
        comment.setContent(request.getContent());
        comment.setAuthor(request.getAuthor());
        comment.setCreatedAt(LocalDateTime.now());

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment result = commentService.createComment(pasteHash, request);

        assertNotNull(result);
        assertEquals(pasteHash, result.getPasteHash());
        assertEquals(request.getContent(), result.getContent());
        assertEquals(request.getAuthor(), result.getAuthor());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void getCommentsByPasteHash_ShouldReturnListOfComments() {
        String pasteHash = "testPasteHash";
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setPasteHash(pasteHash);
        comment1.setContent("Comment 1");
        comment1.setAuthor("Author 1");

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setPasteHash(pasteHash);
        comment2.setContent("Comment 2");
        comment2.setAuthor("Author 2");

        List<Comment> comments = Arrays.asList(comment1, comment2);

        when(commentRepository.findByPasteHashOrderByCreatedAtDesc(pasteHash)).thenReturn(comments);

        List<Comment> result = commentService.getCommentsByPasteHash(pasteHash);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(comment1, result.get(0));
        assertEquals(comment2, result.get(1));
        verify(commentRepository, times(1)).findByPasteHashOrderByCreatedAtDesc(pasteHash);
    }
}