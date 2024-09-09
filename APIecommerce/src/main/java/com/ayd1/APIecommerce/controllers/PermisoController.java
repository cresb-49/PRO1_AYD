/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.Permiso;
import com.ayd1.APIecommerce.services.PermisoService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api/permiso/private")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @Operation(summary = "Obtener todos los permisos existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = Permiso.class
                                    )
                            )
                    )
                }
        ),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content
        )
    })
    @GetMapping("/getPermisos")
    public ResponseEntity<?> getPermisos() {
        try {
            List<Permiso> respuesta = this.permisoService.getPermisos();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }
}
