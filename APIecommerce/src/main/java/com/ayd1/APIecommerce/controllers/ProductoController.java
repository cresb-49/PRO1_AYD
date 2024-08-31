package com.ayd1.APIecommerce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import com.ayd1.APIecommerce.services.ProductoService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDto.class))}),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @GetMapping("/productos")
    public ResponseEntity<?> getProdutos() {
        try {
            List<ProductoDto> respuesta = productoService.getProductos();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, ex.getMessage(), null, null, null).sendResponse();
        }
    }

    @Operation(summary = "Obtener un producto por ID", description = "Devuelve los detalles de un producto específico según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDto.class))}),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @GetMapping("/producto/{id}")
    public ResponseEntity<?> getProducto(@Parameter(description = "ID del producto a buscar", required = true) @PathVariable Long id) {
        try {
            ProductoDto respuesta = productoService.getProductoDto(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, ex.getMessage(), null, null, null).sendResponse();
        }
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto con los detalles proporcionados en el cuerpo de la solicitud y las imágenes proporcionadas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDto.class))}),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/producto/private/crearProducto")
    public ResponseEntity<?> crearProducto(
            @Parameter(description = "Detalles del producto a crear", required = true)
            @ModelAttribute Producto crear,
            @Parameter(description = "Imágenes del producto", required = true)
            @RequestParam("files") List<MultipartFile> files) {
        try {
            ProductoDto respuesta = productoService.createProducto(crear, files);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, ex.getMessage(), null, null, null).sendResponse();
        }
    }

    @Operation(summary = "Actualizar un producto", description = "Actualiza los detalles de un producto existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDto.class))}),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PatchMapping("/producto/private/actualizarProducto")
    public ResponseEntity<?> actualizarProducto(
            @Parameter(description = "Detalles del producto a actualizar", required = true)
            @RequestBody Producto actualizar) {
        try {
            ProductoDto respuesta = productoService.updateProducto(actualizar);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, ex.getMessage(), null, null, null).sendResponse();
        }
    }

    @Operation(summary = "Actualizar imágenes de un producto", description = "Actualiza las imágenes de un producto existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imágenes actualizadas exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDto.class))}),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/producto/private/actualizarImgProd")
    public ResponseEntity<?> actualizarImgProducto(
            @Parameter(description = "ID del producto cuyas imágenes se van a actualizar", required = true)
            @RequestParam("id") Long id,
            @Parameter(description = "Nuevas imágenes del producto", required = true)
            @RequestParam("files") List<MultipartFile> files) {
        try {
            if (files.isEmpty()) {
                throw new IllegalArgumentException("No files provided");
            }
            ProductoDto respuesta = productoService.actualizarImagenes(id, files);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, ex.getMessage(), null, null, null).sendResponse();
        }
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto existente según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @DeleteMapping("/producto/private/eliminarProducto/{id}")
    public ResponseEntity<?> eliminarUsuario(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable Long id) {
        try {
            String confirmacion = this.productoService.eliminarProducto(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", confirmacion, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, ex.getMessage(), null, null, null).sendResponse();
        }
    }
}
