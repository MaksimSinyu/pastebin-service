package com.pastebin.service.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastebin.service.comment.dto.CommentRequest;
import com.pastebin.service.comment.model.Comment;
import com.pastebin.service.comment.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

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
        String pasteHash = "testPasteHash";
        CommentRequest request = new CommentRequest();
        request.setContent("Test content");
        request.setAuthor("Test author");

        Comment createdComment = new Comment();
        createdComment.setId(1L);
        createdComment.setPasteHash(pasteHash);
        createdComment.setContent(request.getContent());
        createdComment.setAuthor(request.getAuthor());

        when(commentService.createComment(eq(pasteHash), eq(request))).thenReturn(createdComment);

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
    void getCommentsByPasteHash_ShouldReturnListOfComments() throws Exception {
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

        when(commentService.getCommentsByPasteHash(pasteHash)).thenReturn(comments);

        mockMvc.perform(get("/api/v1/comments/paste/{pasteHash}", pasteHash))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(comment1.getId()))
                .andExpect(jsonPath("$[0].pasteHash").value(comment1.getPasteHash()))
                .andExpect(jsonPath("$[0].content").value(comment1.getContent()))
                .andExpect(jsonPath("$[0].author").value(comment1.getAuthor()))
                .andExpect(jsonPath("$[1].id").value(comment2.getId()))
                .andExpect(jsonPath("$[1].pasteHash").value(comment2.getPasteHash()))
                .andExpect(jsonPath("$[1].content").value(comment2.getContent()))
                .andExpect(jsonPath("$[1].author").value(comment2.getAuthor()));
    }
}