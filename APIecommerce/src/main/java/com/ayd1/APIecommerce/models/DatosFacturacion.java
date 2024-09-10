/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "datos_facturacion")
@SQLDelete(sql = "UPDATE datos_facturacion SET deleted_at = NULL WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class DatosFacturacion extends Auditor {

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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private Venta venta;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "usuario", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    public DatosFacturacion(String nit, String nombre, Venta venta, Usuario usuario) {
        this.nit = nit;
        this.nombre = nombre;
        this.venta = venta;
        this.usuario = usuario;
    }

    public DatosFacturacion() {
    }

    public DatosFacturacion(Long id) {
        super(id);
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
