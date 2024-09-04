/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.Imagen;
import com.ayd1.APIecommerce.services.ImagenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api")
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @Operation(summary = "Obtiene la imagen de un producto", description
            = "Obtiene la imagen de un producto segun el id de la imagen.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se realiza la venta"),
        @ApiResponse(responseCode = "400", description = "Error en cualquier parte de la venta")
    })
    @GetMapping("/imagenes/public/getImage/{id}")
    @ResponseBody
    public ResponseEntity<?> getImage(@PathVariable Long id) throws Exception {

        try {
            Imagen respuesta = this.imagenService.getImagen(id);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", respuesta.getMimeType()); // O ajusta según el tipo de imagen
            headers.add("Content-Disposition", "inline; filename=image."
                    + respuesta.getExtension());
            return new ResponseEntity<>(respuesta.getImagen(), headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
