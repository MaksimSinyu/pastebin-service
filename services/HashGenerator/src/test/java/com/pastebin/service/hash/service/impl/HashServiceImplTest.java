package com.pastebin.service.hash.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashServiceImplTest {

    private HashServiceImpl hashService;

    @BeforeEach
    void setUp() {
        hashService = new HashServiceImpl();
    }

    @Test
    void generateHash_ShouldReturnNonEmptyString() {
        String data = "Test data";
        String hash = hashService.generateHash(data);
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    @Test
    void generateHash_ShouldReturnConsistentHashForSameInput() {
        String data = "Test data";
        String hash1 = hashService.generateHash(data);
        String hash2 = hashService.generateHash(data);
        assertEquals(hash1, hash2);
    }

    @Test
    void generateHash_ShouldReturnDifferentHashesForDifferentInputs() {
        String data1 = "Test data 1";
        String data2 = "Test data 2";
        String hash1 = hashService.generateHash(data1);
        String hash2 = hashService.generateHash(data2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    void generateHash_ShouldHandleEmptyString() {
        String data = "";
        String hash = hashService.generateHash(data);
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    @Test
    void generateHash_ShouldHandleNullInput() {
        assertThrows(NullPointerException.class, () -> hashService.generateHash(null));
    }
}