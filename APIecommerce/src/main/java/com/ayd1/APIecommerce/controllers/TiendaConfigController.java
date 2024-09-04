/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.Envio;
import com.ayd1.APIecommerce.models.TiendaConfig;
import com.ayd1.APIecommerce.models.dto.TiendaConfigDto;
import com.ayd1.APIecommerce.services.TiendaConfigService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api")
public class TiendaConfigController {

    @Autowired
    private TiendaConfigService tiendaConfigService;

    @Operation(summary = "Obtiene la imagen de la tienda ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen obtenida exitosamente",
                content = {
                    @Content(mediaType = "image/png"),
                    @Content(mediaType = "image/jpeg"),
                    @Content(mediaType = "image/webp"),
                    @Content(mediaType = "image/gif")
                }
        ),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @GetMapping("/tienda_config/public/getImagenTienda")
    @ResponseBody
    public ResponseEntity<?> getImagenTienda() {
        try {
            TiendaConfig respuesta = this.tiendaConfigService.getTiendaConfig();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", respuesta.getMimeTypeImg()); // O ajusta según el tipo de imagen
            headers.add("Content-Disposition", "inline; filename=image."
                    + respuesta.getExtension());

            return new ResponseEntity<>(respuesta.getImagenTienda(), headers, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Error interno del servidor.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtiene la configuracion de la tienda ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuracion de la tienda obtenida exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TiendaConfigDto.class))
                }
        ),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @GetMapping("/tienda_config/public/getTiendaConfig")
    @ResponseBody
    public ResponseEntity<?> getTiendaConfig() {
        try {
            TiendaConfigDto respuesta = this.tiendaConfigService.getTiendaConfigDto();

            return new ApiBaseTransformer(HttpStatus.OK, "OK",
                    respuesta, null, null).sendResponse();

        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Actualiza la informacion de la tienda",
            description = "Actualiza SOlO la informacion textual de la configuracion de la tienda,"
            + " esto quiere decir que no se actualizara la imagen de la tienda en este metodo,"
            + "para actualizar la imagen de la tienda utilizar la ruta:"
            + " \"/tienda_config/private/actualizarImagenDeLaTienda\"")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuracion de la tienda obtenida exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TiendaConfigDto.class))
                }
        ),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PatchMapping("/tienda_config/private/actualizarConfiguracion")
    @ResponseBody
    public ResponseEntity<?> actualizarConfiguracion(
            @Parameter(description = "Detalles de la configuracion a actualizar", required = true)
            @RequestBody TiendaConfig actualizar) {
        try {
            TiendaConfigDto respuesta = this.tiendaConfigService.actualizarTiendaConfig(
                    actualizar);

            return new ApiBaseTransformer(HttpStatus.OK, "OK",
                    respuesta, null, null).sendResponse();

        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Actualiza la imagen de la tienda",
            description = "Actualiza SOlO la imagen de la tienda"
            + "para actualizar la demas informacion de la tienda utilizar la ruta:"
            + " \"/tienda_config/private/actualizarConfiguracion\"")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Configuracion de la tienda con la ruta de la imagen para usarse como src.",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TiendaConfigDto.class))
                }
        ),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/tienda_config/private/actualizarImagenDeLaTienda")
    @ResponseBody
    public ResponseEntity<?> actualizarImagenDeLaTienda(
            @Parameter(description = "Nueva imagen de la tienda", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            TiendaConfigDto respuesta = this.tiendaConfigService.actualizarImagenDeLaTienda(
                    file);

            return new ApiBaseTransformer(HttpStatus.OK, "OK",
                    respuesta, null, null).sendResponse();

        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }
}
