/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "tienda_config")
@DynamicUpdate
public class TiendaConfig extends Auditor {

    @Column(name = "nombre_tienda", length = 250, unique = false)
    @NotBlank(message = "El nombre de la tienda no puede estar vacío.")
    @NotNull(message = "El nombre de la tienda no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre de la tienda debe tener entre 1 y 250 caracteres.")
    private String nombreTienda;

    @Column(name = "imagen_tienda", nullable = false, length = Integer.MAX_VALUE)
    @Lob
    @NotNull(message = "La imagen del producto no puede ser nula.")
    private byte[] imagenTienda;//imagen que debera indicar la herramienta seleccionada

    public TiendaConfig(Long id) {
        super(id);
    }

    public TiendaConfig(String nombreTienda, byte[] imagenTienda) {
        this.nombreTienda = nombreTienda;
        this.imagenTienda = imagenTienda;
    }

    public TiendaConfig() {
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public byte[] getImagenTienda() {
        return imagenTienda;
    }

    public void setImagenTienda(byte[] imagenTienda) {
        this.imagenTienda = imagenTienda;
    }

}
