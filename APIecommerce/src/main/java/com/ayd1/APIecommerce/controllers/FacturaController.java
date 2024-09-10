/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.models.Venta;
import com.ayd1.APIecommerce.models.request.VentaRequest;
import com.ayd1.APIecommerce.services.FacturaService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Operation(summary = "Registrar venta", description
            = "Resgistra una venta en el sistema, automaticmante genera la factura automaticamente"
            + " al igual que la linea de venta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se realiza la venta"),
        @ApiResponse(responseCode = "400", description = "Error en cualquier parte de la venta")
    })
    @PostMapping("/facturacion/cliente/generarCompra")
    @ResponseBody
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest ventaRequest) {
        try {
            byte[] factura = facturaService.guardarVenta(ventaRequest);
            // Configuramos los headers de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "Factura.pdf");
            // Devolvemos el PDF en la respuesta HTTP
            return new ResponseEntity<>(factura, headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    null, null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Obtiene una factura en pdf")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se obtiene una factura en pdf"),
        @ApiResponse(responseCode = "400", description = "Error en cualquier parte de la busqueda")
    })
    @GetMapping("/facturacion/private/all/getVenta/{id}")
    @ResponseBody
    public ResponseEntity<?> getFactura(@PathVariable Long id) {
        try {
            byte[] factura = facturaService.getFactura(id);
            // Configuramos los headers de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "Factura.pdf");
            // Devolvemos el PDF en la respuesta HTTP
            return new ResponseEntity<>(factura, headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    null, null, null, ex.getMessage()).sendResponse();
        }
    }

    @GetMapping("/ventas/usuario/cliente/{id}")
    public ResponseEntity<?> getVentasByUser(@PathVariable Long id) {
        try {
            List<Venta> data = facturaService.getVentasByUser(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", data, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    null, null, null, ex.getMessage()).sendResponse();
        }
    }

    @GetMapping("/venta/private/all/{id}")
    public ResponseEntity<?> getProductsVenta(@PathVariable Long id) {
        try {
            Venta data = facturaService.getVenta(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", data, null, null).sendResponse();
        } catch (Exception e) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, null, null, null, e.getMessage()).sendResponse();
        }
    }

}
