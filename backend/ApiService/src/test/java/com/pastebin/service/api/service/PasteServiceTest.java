package com.pastebin.service.api.service;

import com.pastebin.service.api.client.MinioClient;
import com.pastebin.service.api.model.Paste;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class PasteServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private MinioClient minioClient;

    @Autowired
    private PasteService pasteService;

    @Value("${hash.generator.url}")
    private String hashGeneratorUrl;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Test
    void createPaste_shouldReturnPasteWithHashAndData() throws Exception {
        String data = "Test data";
        String hash = "testHash";

        when(restTemplate.postForObject(eq(hashGeneratorUrl), eq(data), eq(String.class)))
                .thenReturn(hash);

        doNothing().when(minioClient).putObject(eq(bucketName), eq(hash), any(byte[].class));

        Paste paste = pasteService.createPaste(data);

        assertNotNull(paste);
        assertEquals(hash, paste.getHash());
        assertEquals(data, paste.getData());

        verify(restTemplate).postForObject(eq(hashGeneratorUrl), eq(data), eq(String.class));
        verify(minioClient).putObject(eq(bucketName), eq(hash), any(byte[].class));
    }
}