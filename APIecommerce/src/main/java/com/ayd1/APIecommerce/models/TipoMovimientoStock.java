/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "tipo_movimiento_stock")
public class TipoMovimientoStock extends Auditor {

    @Column(name = "nombre", length = 250, unique = true)
    @NotBlank(message = "El nombre del tipo no puede estar vacío.")
    @NotNull(message = "El nombre del tipo no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del tipo debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @OneToMany(mappedBy = "tipo", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<MovimientoStock> movimientos;

    public TipoMovimientoStock() {
    }

    public TipoMovimientoStock(String nombre) {
        this.nombre = nombre;
    }

    public TipoMovimientoStock(Long id) {
        super(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MovimientoStock> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoStock> movimientos) {
        this.movimientos = movimientos;
    }

}
