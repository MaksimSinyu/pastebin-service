package com.pastebin.service.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastebin.service.comment.dto.CommentRequest;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createComment_ShouldReturnCreatedComment() throws Exception {
        String pasteHash = "testHash";
        CommentRequest request = new CommentRequest();
        request.setContent("Test content");
        request.setAuthor("Test author");

        Comment createdComment = new Comment();
        createdComment.setId(1L);
        createdComment.setPasteHash(pasteHash);
        createdComment.setContent(request.getContent());
        createdComment.setAuthor(request.getAuthor());

        when(commentService.createComment(eq(pasteHash), eq(request.getContent()), eq(request.getAuthor())))
                .thenReturn(createdComment);

        mockMvc.perform(post("/api/v1/comments/{pasteHash}", pasteHash)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdComment.getId()))
                .andExpect(jsonPath("$.pasteHash").value(createdComment.getPasteHash()))
                .andExpect(jsonPath("$.content").value(createdComment.getContent()))
                .andExpect(jsonPath("$.author").value(createdComment.getAuthor()));
    }

    @Test
    void getComment_ShouldReturnComment() throws Exception {
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setContent("Test content");
        comment.setAuthor("Test author");

        when(commentService.getComment(commentId)).thenReturn(comment);

        mockMvc.perform(get("/api/v1/comments/{id}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()))
                .andExpect(jsonPath("$.content").value(comment.getContent()))
                .andExpect(jsonPath("$.author").value(comment.getAuthor()));
    }

    @Test
    void updateComment_ShouldReturnUpdatedComment() throws Exception {
        Long commentId = 1L;
        CommentRequest request = new CommentRequest();
        request.setContent("Updated content");

        Comment updatedComment = new Comment();
        updatedComment.setId(commentId);
        updatedComment.setContent(request.getContent());

        when(commentService.updateComment(eq(commentId), eq(request.getContent())))
                .thenReturn(updatedComment);

        mockMvc.perform(put("/api/v1/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedComment.getId()))
                .andExpect(jsonPath("$.content").value(updatedComment.getContent()));
    }

    @Test
    void deleteComment_ShouldReturnNoContent() throws Exception {
        Long commentId = 1L;

        mockMvc.perform(delete("/api/v1/comments/{id}", commentId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCommentsForPaste_ShouldReturnPageOfComments() throws Exception {
        String pasteHash = "testHash";
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPasteHash(pasteHash);
        comment.setContent("Test content");
        comment.setAuthor("Test author");

        Page<Comment> commentPage = new PageImpl<>(Collections.singletonList(comment));

        when(commentService.getCommentsForPaste(eq(pasteHash), any())).thenReturn(commentPage);

        mockMvc.perform(get("/api/v1/comments/paste/{pasteHash}", pasteHash))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(comment.getId()))
                .andExpect(jsonPath("$.content[0].pasteHash").value(comment.getPasteHash()))
                .andExpect(jsonPath("$.content[0].content").value(comment.getContent()))
                .andExpect(jsonPath("$.content[0].author").value(comment.getAuthor()));
    }
}