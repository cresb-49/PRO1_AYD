package com.ayd1.APIecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.services.ProductoService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;



@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public ApiBaseTransformer getProdutos(@RequestParam String param) {
        return new ApiBaseTransformer(200, "OK", null, null, null);
    }
    
    @PostMapping("/producto/{id}")
    public ApiBaseTransformer getProducto(@PathVariable Long id) {
        return new ApiBaseTransformer(200, "OK", null, null, null);
    }
    
}
