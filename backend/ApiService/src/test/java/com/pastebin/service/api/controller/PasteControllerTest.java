package com.pastebin.service.api.controller;

import com.pastebin.service.api.service.PasteService;
import com.pastebin.service.api.model.Paste;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PasteController.class)
class PasteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasteService pasteService;

    @Test
    void createPaste_shouldReturnCreatedPaste() throws Exception {
        String data = "Test data";
        String hash = "testHash";
        Paste paste = new Paste(hash, data);

        when(pasteService.createPaste(anyString())).thenReturn(paste);

        mockMvc.perform(post("/paste")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": \"" + data + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hash").value(hash))
                .andExpect(jsonPath("$.data").value(data));
    }
}