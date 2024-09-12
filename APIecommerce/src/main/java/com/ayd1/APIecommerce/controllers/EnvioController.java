/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.Envio;
import com.ayd1.APIecommerce.services.EnvioService;
import com.ayd1.APIecommerce.tools.ValidadorPermiso;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api")
public class EnvioController {

        @Autowired
        private EnvioService envioService;
        @Autowired
        private ValidadorPermiso validadorPermiso;

        @Operation(summary = "Obtener un envio por ID", description = "Devuelve los detalles de un envio específico según su ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Envio obtenido exitosamente", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class)) }),
                        @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
        })
        @GetMapping("/envio/protected/getEnvio/{id}")
        public ResponseEntity<?> getEnvio(
                        @Parameter(description = "ID del envio a buscar", required = true) @PathVariable Long id) {
                try {
                        this.validadorPermiso.verificarPermiso();
                        Envio respuesta = envioService.getEnvio(id);
                        return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
                } catch (Exception ex) {
                        return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage())
                                        .sendResponse();
                }
        }

        @Operation(summary = "Obtener un envio por ID", description = "Devuelve los detalles de un envio específico según su ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Envio obtenido exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Envio.class)))),
                        @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
        })
        @GetMapping("/envio/protected/getEnvios")
        public ResponseEntity<?> getEnvios() {
                try {
                        this.validadorPermiso.verificarPermiso();
                        List<Envio> respuesta = envioService.getEnvios();
                        return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
                } catch (Exception ex) {
                        return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage())
                                        .sendResponse();
                }
        }

        @Operation(summary = "Obtener los envios exitentes segun su estado (PENDIENTE, ENTREGADO)", description = "Devuelve los detalles de los envios existentes filtrados por su estado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Envio obtenido exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Envio.class)))),
                        @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
        })
        @GetMapping("/envio/protected/getEnviosFiltrados/{estado}")
        public ResponseEntity<?> getEnviosFiltrados(
                        @Parameter(description = "Estado de los envios", required = true) @PathVariable String estado) {
                try {
                        List<Envio> respuesta = envioService.getEnviosFiltradosPorEstado(estado);
                        return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
                } catch (Exception ex) {
                        return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage())
                                        .sendResponse();
                }
        }

        @Operation(summary = "Actualizar el estado del envio", description = "Actualiza el estado de entrega de un envio, si el "
                        + "estado de envio es ENTREGADO entonces pasara a ser PENDIENTE "
                        + "y viceversa.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Envio actualizado exitosamente", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class)) }),
                        @ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content)
        })
        @PatchMapping("/envio/protected/cambiarEstadoEnvio/{id}")
        public ResponseEntity<?> actualizarEnvio(
                        @Parameter(description = "ID del envio a actualizar su estado", required = true) @PathVariable Long id) {
                try {
                        this.validadorPermiso.verificarPermiso();
                        Envio respuesta = envioService.updateEnvio(id);
                        return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
                } catch (Exception ex) {
                        return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage())
                                        .sendResponse();
                }
        }
}
