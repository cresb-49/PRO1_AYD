/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "datos_facturacion")
public class DatosFacturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nit", length = 250, unique = false)
    @NotBlank(message = "El nit no puede estar vacío.")
    @NotNull(message = "El nit no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nit debe tener entre 1 y 250 caracteres.")
    private String nit;

    @Column(name = "nombre", length = 250, unique = false)
    @NotBlank(message = "El nombre no puede estar vacío.")
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombe debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "venta", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Venta venta;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public DatosFacturacion(Long id, String nit, String nombre, Venta venta, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.venta = venta;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public DatosFacturacion(Long id, String nit, String nombre, Venta venta) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.venta = venta;
    }

    public DatosFacturacion() {
    }

    public DatosFacturacion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
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

}
