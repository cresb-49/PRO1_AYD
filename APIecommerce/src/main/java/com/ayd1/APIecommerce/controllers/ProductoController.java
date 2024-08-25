package com.ayd1.APIecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.services.ProductoService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public ResponseEntity<?> getProdutos(@RequestParam String param) {
        return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
    }

    @PostMapping("/producto/{id}")
    public ResponseEntity<?> getProducto(@PathVariable Long id) {
        return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
    }

    @PostMapping("/producto/private/crearProducto")
    public ResponseEntity<?> crearUsuario(@RequestBody Producto crear) {
        try {
            Producto respuesta = productoService.createProducto(crear);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

}
