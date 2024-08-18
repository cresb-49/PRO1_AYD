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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "tipo_movimiento_stock")
public class TipoMovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", length = 250, unique = true)
    @NotBlank(message = "El nombre del tipo no puede estar vacío.")
    @NotNull(message = "El nombre del tipo no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del tipo debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "tipo", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<MovimientoStock> movimientos;

    public TipoMovimientoStock() {
    }

    public TipoMovimientoStock(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoMovimientoStock(Long id, String nombre, List<MovimientoStock> movimientos) {
        this.id = id;
        this.nombre = nombre;
        this.movimientos = movimientos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<MovimientoStock> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoStock> movimientos) {
        this.movimientos = movimientos;
    }

}
