/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.dto.reports.ReporteClientesFrecuentesDto;
import com.ayd1.APIecommerce.models.dto.reports.ReporteInventarioDto;
import com.ayd1.APIecommerce.models.dto.reports.ReportePedidoDto;
import com.ayd1.APIecommerce.models.dto.reports.ReporteVentasDto;
import com.ayd1.APIecommerce.models.request.ReporteExportRequest;
import com.ayd1.APIecommerce.models.request.ReporteRequest;
import com.ayd1.APIecommerce.services.Service;
import com.ayd1.APIecommerce.services.reportes.ReporteClientesFrecuentesService;
import com.ayd1.APIecommerce.services.reportes.ReporteInventarioService;
import com.ayd1.APIecommerce.services.reportes.ReportePedidosService;
import com.ayd1.APIecommerce.services.reportes.ReporteVentasService;
import com.ayd1.APIecommerce.tools.ValidadorPermiso;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api/reporte/protected")
public class ReporteController {

    @Autowired
    private ValidadorPermiso validadorPermiso;
    @Autowired
    private Service service;
    @Autowired
    private ReporteVentasService reporteVentasService;
    @Autowired
    private ReporteInventarioService reporteInventarioService;
    @Autowired
    private ReportePedidosService reportePedidosService;
    @Autowired
    private ReporteClientesFrecuentesService reporteClientesFrecuentes;

    @Operation(summary = "El reporte de ventas en el formato especificado.",
            description = "Crea un reporte de ventas desde "
            + "la primera fecha hasta la segunda fecha segun el reporte especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReporteVentasDto.class))
                }),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/exportar")
    public ResponseEntity<?> generarReporteExportado(
            @Parameter(description = "Detalles del reporte a generar", required = true)
            @RequestBody ReporteExportRequest reporteDails) {

        try {
            this.service.validar(reporteDails);
            this.validadorPermiso.verificarPermiso(reporteDails.getTipoReporte());
            byte[] reporte;
            switch (reporteDails.getTipoReporte()) {
                case "reporteVentas" -> {
                    reporte
                            = this.reporteVentasService
                                    .exportarReporteVentas(reporteDails);
                }
                case "reporteInventario" -> {
                    reporte
                            = this.reporteInventarioService
                                    .exportarReporteInventario(reporteDails);
                }
                case "reporteClientesFrecuentes" -> {
                    reporte
                            = this.reporteClientesFrecuentes
                                    .exportarReporteClientesFrecuentes(reporteDails);
                }
                case "reportePedidos" -> {
                    reporte
                            = this.reportePedidosService
                                    .exportarReporteDePedidos(reporteDails);
                }
                default -> {
                    throw new AssertionError();
                }
            }
            // Configuramos los headers de la respuesta
            HttpHeaders headers = new HttpHeaders();
            switch (reporteDails.getTipoExporte()) {
                case "excel" -> {
                    headers.set(HttpHeaders.CONTENT_TYPE,
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    headers.set(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=reporte.xlsx");
                }
                case "word" -> {
                    headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte.docx");
                }
                default -> {
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    headers.setContentDispositionFormData("inline", "reporte.pdf");
                }
            }

            // Devolvemos el PDF en la respuesta HTTP
            return new ResponseEntity<>(reporte, headers, HttpStatus.OK);

        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }

    }

    @Operation(summary = "El reporte de ventas.",
            description = "Crea un reporte de ventas desde la primera fecha hasta la segunda fecha.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReporteVentasDto.class))
                }),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/generarReporteVentas")
    public ResponseEntity<?> generarReporteVentas(
            @Parameter(description = "Detalles del reporte a generar", required = true)
            @RequestBody ReporteRequest reporte) {
        try {
            this.validadorPermiso.verificarPermiso();
            ReporteVentasDto respuesta = this.reporteVentasService.generarReporteVentas(reporte);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "El reporte de inventario.",
            description = "Crea un reporte de inventario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReporteInventarioDto.class))
                }),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/generarReporteInventario")
    public ResponseEntity<?> generarReporteInventario() {
        try {
            this.validadorPermiso.verificarPermiso();
            ReporteInventarioDto respuesta = this.reporteInventarioService.generarReporteInventario();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "El reporte de clientes frecuentes.",
            description = "Crea un reporte de los clientes mas frecuentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReporteClientesFrecuentesDto.class))
                }),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/generarReporteClientesFrecuentes")
    public ResponseEntity<?> generarReporteClientesFrecuentes(
            @Parameter(description = "Detalles del reporte a generar", required = true)
            @RequestBody ReporteRequest reporteRequest) {
        try {
            this.validadorPermiso.verificarPermiso();
            ReporteClientesFrecuentesDto respuesta
                    = this.reporteClientesFrecuentes.
                            generarReporteClientesFrecuentes(reporteRequest);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "El reporte de los pedidos.",
            description = "Crea un reporte de los pedidos existentes en un lapso de tiempo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportePedidoDto.class))
                }),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
                content = @Content)
    })
    @PostMapping("/generarReportePedidos")
    public ResponseEntity<?> generarReportePedidos(
            @Parameter(description = "Detalles del reporte a generar", required = true)
            @RequestBody ReporteExportRequest reporteRequest) {
        try {
            this.validadorPermiso.verificarPermiso();
            ReportePedidoDto respuesta
                    = this.reportePedidosService.
                            generarReporteDePedidos(reporteRequest);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }
}
