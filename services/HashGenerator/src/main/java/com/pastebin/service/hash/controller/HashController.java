package com.pastebin.service.hash.controller;

import com.pastebin.service.hash.service.HashService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HashController {
    private final HashService hashService;

    @PostMapping("/generate-hash")
    public String generateHash(@RequestBody(required = false) String data) {
        return hashService.generateHash(data != null ? data : "");
    }
}