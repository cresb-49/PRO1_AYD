package com.ayd1.APIecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.services.CategoriaService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categoria/{id}")
    public ApiBaseTransformer getCategoria(@PathVariable Long id) {
        Object data = categoriaService.getCategoria(id);
        return new ApiBaseTransformer(200,"OK",data,null,null);
    }
}
