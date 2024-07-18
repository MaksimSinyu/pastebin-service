package com.pastebin.service.api.controller;

import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paste")
public class PasteController {

    @Autowired
    private PasteService pasteService;

    @PostMapping
    public ResponseEntity<Paste> createPaste(@RequestBody String data) {
        if (data == null || data.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Paste paste = pasteService.createPaste(data);
            return ResponseEntity.ok(paste);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{hash}")
    public ResponseEntity<Paste> getPaste(@PathVariable String hash) {
        try {
            Paste paste = pasteService.getPaste(hash);
            if (paste != null) {
                return ResponseEntity.ok(paste);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}