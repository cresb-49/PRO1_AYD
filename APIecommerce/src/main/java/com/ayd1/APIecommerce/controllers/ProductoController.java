package com.ayd1.APIecommerce.controllers;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import com.ayd1.APIecommerce.services.CategoriaService;
import com.ayd1.APIecommerce.services.ProductoService;
import com.ayd1.APIecommerce.tools.ValidadorPermiso;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ValidadorPermiso validadorPermiso;

    @Autowired
    private EntityManager entityManager;

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ProductoDto.class
                                    )
                            )
                    )
                }
        ),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content
        )
    })
    @GetMapping("/productos/public/getProductos")
    public ResponseEntity<?> getProdutos() {
        try {
            List<ProductoDto> respuesta = productoService.getProductosDto();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Obtener todos los 10 productos mas nuevos en la bd"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description
                = "Lista de productos obtenida exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ProductoDto.class
                                    )
                            )
                    )
                }
        ),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content
        )
    })
    @GetMapping("/productos/public/getDiezProductosMasReciente")
    public ResponseEntity<?> getDiezProductosMasReciente() {
        try {
            List<ProductoDto> respuesta = productoService.getDiezProductosMasReciente();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
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
    @GetMapping("/producto/public/getProducto/{id}")
    public ResponseEntity<?> getProducto(@Parameter(description = "ID del producto a buscar", required = true) @PathVariable Long id) {
        try {
            ProductoDto respuesta = productoService.getProductoDto(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
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
    @PostMapping("/producto/protected/crearProducto")
    public ResponseEntity<?> crearProducto(
            @Parameter(description = "Detalles del producto a crear", required = true)
            @ModelAttribute Producto crear,
            @Parameter(description = "Imágenes del producto", required = true)
            @RequestParam("files") List<MultipartFile> files) {
        try {
            this.validadorPermiso.verificarPermiso();
            ProductoDto respuesta = productoService.createProducto(crear, files);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
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
    @PatchMapping("/producto/protected/actualizarProducto")
    public ResponseEntity<?> actualizarProducto(
            @Parameter(description = "Detalles del producto a actualizar", required = true)
            @RequestBody Producto actualizar) {
        try {
            this.validadorPermiso.verificarPermiso();
            ProductoDto respuesta = productoService.updateProducto(actualizar);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
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
    @PostMapping("/producto/protected/actualizarImgProd")
    public ResponseEntity<?> actualizarImgProducto(
            @Parameter(description = "ID del producto cuyas imágenes se van a actualizar", required = true)
            @RequestParam("id") Long id,
            @Parameter(description = "Nuevas imágenes del producto", required = true)
            @RequestParam("files") List<MultipartFile> files) {
        try {
            this.validadorPermiso.verificarPermiso();
            if (files.isEmpty()) {
                throw new IllegalArgumentException("No files provided");
            }
            ProductoDto respuesta = productoService.actualizarImagenes(id, files);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
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
    @DeleteMapping("/producto/protected/eliminarProducto/{id}")
    public ResponseEntity<?> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable Long id) {
        try {
            this.validadorPermiso.verificarPermiso();
            String confirmacion = this.productoService.eliminarProducto(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", confirmacion, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Devuelve todos los productos con 5 o menos esxistencias",
            description = "")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de productos",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(
                                        implementation = ProductoDto.class
                                )
                        )
                )),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @GetMapping("/producto/protected/getStockBajo")
    public ResponseEntity<?> getStockBajo() {
        try {
            List<ProductoDto> respuesta = this.productoService.
                    getProductosConBajaExistencia();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Devuelve todos los productos que pertenecen a una categoria a los padres.",
            description = "Devuelve un Array de productos, dichos productos seran todos aquellos que pertenecen "
            + "a la categoria especificada. Si se trata de una categoria padre, tambien se enviaran"
            + "todos los productos que pertenecen a las categorias hijas del padre.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de productos",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(
                                        implementation = ProductoDto.class
                                )
                        )
                )),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @GetMapping("/producto/public/categoria/{id}")
    public ResponseEntity<?> buscarPorCategoria(
            @Parameter(description = "ID de la categoria", required = true)
            @PathVariable Long id) throws Exception {

        try {
            Categoria categoria = categoriaService.getCategoria(id);
            List<ProductoDto> productos = productoService.buscarPorCategoria(categoria);
            return new ApiBaseTransformer(HttpStatus.OK, null, productos, null, null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, e.getMessage(), null, null, null).sendResponse();
        }
    }

    @Operation(summary = "Devuelve todos los productos con 5 o menos esxistencias",
            description = "")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de productos",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(
                                        implementation = ProductoDto.class
                                )
                        )
                )),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @GetMapping("/producto/public/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<ProductoDto> productos = productoService.buscarPorNombre(nombre);
            return new ApiBaseTransformer(HttpStatus.OK, null, productos, null, null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, e.getMessage(), null, null, null).sendResponse();
        }
    }

    @GetMapping("/producto/public/precio")
    public ResponseEntity<?> buscarPorRangoDePrecio(@RequestParam Double precioMin, @RequestParam Double precioMax) {
        try {
            List<ProductoDto> productos = productoService.buscarPorRangoDePrecio(precioMin, precioMax);
            return new ApiBaseTransformer(HttpStatus.OK, null, productos, null, null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, e.getMessage(), null, null, null).sendResponse();
        }
    }

    @Operation(summary = "Envia un correo electronico a todos los adminsitradores"
            + " indicando los productos con bajo stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se envio el correo exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    @PostMapping("/producto/private/notificarBajoStock")
    public ResponseEntity<?> notificarBajoStock() {
        try {
            productoService.notificarProductosBajaExistenciaUnaVez();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }
}
