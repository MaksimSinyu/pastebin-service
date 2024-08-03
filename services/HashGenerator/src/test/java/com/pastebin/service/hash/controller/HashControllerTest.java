package com.pastebin.service.hash.controller;

import com.pastebin.service.hash.service.HashService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HashController.class)
class HashControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HashService hashService;

    @Test
    void generateHash_ShouldReturnHashedString() throws Exception {
        String inputData = "Test data";
        String expectedHash = "hashed_test_data";

        when(hashService.generateHash(anyString())).thenReturn(expectedHash);

        mockMvc.perform(post("/generate-hash")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(inputData))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedHash));
    }

    @Test
    void generateHash_ShouldHandleEmptyInput() throws Exception {
        String expectedHash = "hashed_empty_string";

        when(hashService.generateHash("")).thenReturn(expectedHash);

        mockMvc.perform(post("/generate-hash")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedHash));
    }
}