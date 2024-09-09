/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "venta")
public class Venta extends Auditor {

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(nullable = false)
    private Double totalImpuestosPagados;

    @Column(name = "cuota_pago_contra_entrega", nullable = false)
    private Double cuotaPagContraEntrega;

    @Column(name = "cantidad_productos", nullable = false)
    private Integer cantidadProductos;

    @OneToMany(mappedBy = "venta", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<LineaVenta> lineaVentas;

    @OneToOne(mappedBy = "venta", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "factura", nullable = false, unique = true)
    private DatosFacturacion datosFacturacion;

    @OneToOne(mappedBy = "venta")
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "envio", nullable = true, unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private Envio envio;

    public Venta() {
    }

    public Venta(Long id) {
        super(id);
    }

    public Venta(Double valorTotal, Double cuotaPagContraEntrega, Integer cantidadProductos,
            Double totalImpuestosPagados) {
        this.valorTotal = valorTotal;
        this.cuotaPagContraEntrega = cuotaPagContraEntrega;
        this.cantidadProductos = cantidadProductos;
        this.totalImpuestosPagados = totalImpuestosPagados;

    }

    public Double getCuotaPagContraEntrega() {
        return cuotaPagContraEntrega;
    }

    public void setCuotaPagContraEntrega(Double cuotaPagContraEntrega) {
        this.cuotaPagContraEntrega = cuotaPagContraEntrega;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(Integer cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public List<LineaVenta> getLineaVentas() {
        return lineaVentas;
    }

    public void setLineaVentas(List<LineaVenta> lineaVentas) {
        this.lineaVentas = lineaVentas;
    }

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    public Double getTotalImpuestosPagados() {
        return totalImpuestosPagados;
    }

    public void setTotalImpuestosPagados(Double totalImpuestosPagados) {
        this.totalImpuestosPagados = totalImpuestosPagados;
    }

}
