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
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import com.ayd1.APIecommerce.services.ProductoService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public ResponseEntity<?> getProdutos() {
        try {
            List<ProductoDto> respuesta = productoService.getProductos();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<?> getProducto(@PathVariable Long id) {
        try {
            ProductoDto respuesta = productoService.getProducto(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @PostMapping("/producto/private/crearProducto")
    public ResponseEntity<?> crearProducto(@ModelAttribute Producto crear,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            ProductoDto respuesta = productoService.createProducto(crear, files);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @PatchMapping("/producto/private/actualizarProducto")
    public ResponseEntity<?> actualizarProducto(@RequestBody Producto actualizar) {
        try {
            ProductoDto respuesta = productoService.updateProducto(actualizar);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @PostMapping("/producto/private/actualizarImgProd")
    public ResponseEntity<?> actualizarImgProducto(@RequestParam("id") Long id,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                throw new IllegalArgumentException("No files provided");
            }
            ProductoDto respuesta = productoService.actualizarImagenes(id, files);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @DeleteMapping("/producto/private/eliminarProducto/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {

            String confirmacion = this.productoService.eliminarProducto(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK",
                    confirmacion,
                    null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

}
