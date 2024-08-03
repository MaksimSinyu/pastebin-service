package com.pastebin.service.api.service;

import com.pastebin.service.api.config.RedisConfig;
import com.pastebin.service.api.config.TestRedisConfiguration;
import com.pastebin.service.api.model.Paste;
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
@Import({TestRedisConfiguration.class, RedisConfig.class})
class PasteServiceTest {
    @MockBean
    private StorageService storageService;
    @MockBean
    private HashGeneratorClient hashGeneratorClient;
    @MockBean
    private MetricsClient metricsClient;
    @Autowired
    private PasteService pasteService;
    @MockBean
    private CacheService cacheService;

    @Test
    void createPaste_shouldReturnPasteWithHashAndData() {
        metricsClient.incrementPasteCreated();
        String data = "Test data";
        String hash = "testHash";

        when(hashGeneratorClient.generateHash(eq(data))).thenReturn(hash);
        doNothing().when(cacheService).set(anyString(), any(Paste.class), anyLong(), any());

        Paste paste = pasteService.createPaste(data);

        assertNotNull(paste);
        assertEquals(hash, paste.getHash());
        assertEquals(data, paste.getData());

        verify(hashGeneratorClient).generateHash(eq(data));
        verify(storageService).saveObject(eq(hash), any(byte[].class));
        verify(cacheService).set(eq(hash), eq(paste), anyLong(), any());
    }
}
