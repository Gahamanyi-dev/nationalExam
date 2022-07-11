package com.example.server.utils;


import org.springframework.http.ResponseEntity;

public class Formatter {

    public static ResponseEntity<ApiResponse> done() {
        return ResponseEntity.ok(ApiResponse.success("done"));
    }

    public static ResponseEntity<ApiResponse> ok(Object data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}