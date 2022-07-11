package com.example.server.controllers;

import com.example.server.services.ILinksService;
import com.example.server.utils.Formatter;
import com.example.server.utils.dtos.CreateLinkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/links")
public class LinksController {

    private final ILinksService linksService;

    @Autowired
    public LinksController(ILinksService linksService) {
        this.linksService = linksService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(linksService.all());
    }

    @PostMapping ResponseEntity<?> create(@Valid @RequestBody CreateLinkDTO link) {
//        return ResponseEntity.ok(linksService.create(link));
       return Formatter.ok(linksService.create(link));
    }
}
