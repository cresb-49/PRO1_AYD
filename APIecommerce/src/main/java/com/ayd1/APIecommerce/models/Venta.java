/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "valor_total", nullable = false)
    @Min(value = 0, message = "El valor total debe tener como valor mínimo 0.")
    private Double valorTotal;

    @Column(name = "cantidad_productos", nullable = false)
    @Min(value = 1, message = "El cantidad debe tener como valor mínimo 1.")
    private Integer cantidadProductos;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "venta", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<LineaVenta> lineaVentas;

    @OneToMany(mappedBy = "venta", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<DatosFacturacion> datosFacturacion;

    @OneToMany(mappedBy = "venta", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<Envio> envios;

    public Venta(Long id, Double valorTotal, Integer cantidadProductos, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.cantidadProductos = cantidadProductos;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Venta() {
    }

    public Venta(Long id) {
        this.id = id;
    }

    public Venta(Long id, Double valorTotal, Integer cantidadProductos) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.cantidadProductos = cantidadProductos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<LineaVenta> getLineaVentas() {
        return lineaVentas;
    }

    public void setLineaVentas(List<LineaVenta> lineaVentas) {
        this.lineaVentas = lineaVentas;
    }

    public List<DatosFacturacion> getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(List<DatosFacturacion> datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }

}
