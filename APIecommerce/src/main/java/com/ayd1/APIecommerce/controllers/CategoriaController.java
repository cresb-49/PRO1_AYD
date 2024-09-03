package com.ayd1.APIecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.services.CategoriaService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> getCategoria(@PathVariable Long id) {
        try {
            Object data = categoriaService.getCategoria(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", data, null, null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.INTERNAL_SERVER_ERROR, "Error", null,null,e.getMessage()).sendResponse();
        }
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> getCategorias() {
        try {
            List<Categoria> listaCategorias = categoriaService.getCategorias();
            return new ApiBaseTransformer(HttpStatus.OK, "OK",listaCategorias,null,null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, e.getMessage()).sendResponse();
        }
    }

    @PostMapping("/categoria/private/crearCategoria")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria nuevaCategoria) {
        try {
            Categoria categoria = categoriaService.createCategoria(nuevaCategoria);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", categoria,
                    null, null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, e.getMessage()).sendResponse();
        }
    }

    @PatchMapping("/categoria/private/updateCategoria")
    public ResponseEntity<?> actualizarCategoria(@RequestBody Long id, Categoria editCategoria) {
        try {
            Categoria okCategoria = categoriaService.updateCategoria(id, editCategoria);
            return new ApiBaseTransformer(HttpStatus.OK, "OK",
            okCategoria,
            null, null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,"Error",null, null, e.getMessage()).sendResponse();
        }
    }

    @DeleteMapping("/categoria/private/eliminarCategoria")
    public ResponseEntity<?> eliminarCategoria() {
        try {
            return new ApiBaseTransformer(HttpStatus.OK, "OK",null,null,null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, e.getMessage()).sendResponse();
        }
    }
}
