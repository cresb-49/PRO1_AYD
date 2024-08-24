package com.ayd1.APIecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.services.RolService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

@RestController
@RequestMapping("/api")
public class RolController {
    
    @Autowired
    private RolService rolService;

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestParam String param) {
        return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
    }
    
    @PostMapping("/rol/{id}")
    public ResponseEntity<?> getRol(@PathVariable Long id) {
        return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
    }
    
}