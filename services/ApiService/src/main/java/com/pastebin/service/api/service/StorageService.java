package com.pastebin.service.api.service;

public interface StorageService {
    void saveObject(String key, byte[] data);
    byte[] getObject(String key);
}