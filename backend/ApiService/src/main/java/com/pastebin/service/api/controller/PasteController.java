package com.pastebin.service.api.controller;

import com.pastebin.service.api.exception.PasteNotFoundException;
import com.pastebin.service.api.exception.StorageException;
import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.service.PasteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/paste")
@RequiredArgsConstructor
public class PasteController {
    private final PasteService pasteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paste> createPaste(@RequestBody Map<String, String> payload) {
        String data = payload.get("data");
        if (data == null || data.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Paste paste = pasteService.createPaste(data);
        return ResponseEntity.ok(paste);
    }

    @GetMapping(value = "/{hash}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paste> getPaste(@PathVariable String hash) {
        Paste paste = pasteService.getPaste(hash);
        return ResponseEntity.ok(paste);
    }

    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<String> handlePasteNotFound(PasteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<String> handleStorageException(StorageException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}