package com.pastebin.service.api.service;

import com.pastebin.service.api.config.TestRedisConfiguration;
import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.service.impl.MinioStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestRedisConfiguration.class)
class PasteServiceTest {

    @MockBean
    private StorageService storageService;

    @MockBean
    private HashGeneratorClient hashGeneratorClient;

    @Autowired
    private PasteService pasteService;

    @Test
    void createPaste_shouldReturnPasteWithHashAndData() {
        String data = "Test data";
        String hash = "testHash";

        when(hashGeneratorClient.generateHash(eq(data))).thenReturn(hash);
        doNothing().when(storageService).saveObject(eq(hash), any(byte[].class));

        Paste paste = pasteService.createPaste(data);

        assertNotNull(paste);
        assertEquals(hash, paste.getHash());
        assertEquals(data, paste.getData());

        verify(hashGeneratorClient).generateHash(eq(data));
        verify(storageService).saveObject(eq(hash), any(byte[].class));
    }
}