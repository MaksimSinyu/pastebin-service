package com.pastebin.service.hash.service;

import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public interface HashService {
    String generateHash(String data);
}