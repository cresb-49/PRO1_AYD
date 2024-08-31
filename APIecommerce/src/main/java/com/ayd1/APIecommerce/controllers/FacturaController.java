/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.Venta;
import com.ayd1.APIecommerce.models.request.VentaRequest;
import com.ayd1.APIecommerce.repositories.DatosFacturacionRepository;
import com.ayd1.APIecommerce.repositories.LineaVentaRepository;
import com.ayd1.APIecommerce.repositories.VentaRepository;
import com.ayd1.APIecommerce.services.FacturaService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("api")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Operation(summary = "Registrar venta", description
            = "Resgistra una venta en el sistema, automaticmante genera la factura automaticamente"
            + " al igual que la linea de venta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña recuperada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/facturacion/public/generarCompra")
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest ventaRequest) {
        try {
            String respuesta = facturaService.guardarVenta(ventaRequest);
            return new ApiBaseTransformer(HttpStatus.OK, "OK",
                    respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(), null, null, null).sendResponse();
        }
    }

}
