package com.pastebin.service.api.controller;

import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/paste")
public class PasteController {

    @Autowired
    private PasteService pasteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paste> createPaste(@RequestBody Map<String, String> payload) {
        String data = payload.get("data");
        if (data == null || data.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Paste paste = pasteService.createPaste(data);
            return ResponseEntity.ok(paste);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{hash}", produces = MediaType.APPLICATION_JSON_VALUE)
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