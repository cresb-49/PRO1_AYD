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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "producto")
@DynamicUpdate
@SQLDelete(sql = "UPDATE producto SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Producto extends Auditor {

    @Column(name = "nombre", length = 250, unique = false)
    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del producto debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "LONGTEXT")
    private String descripcion;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "categoria", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @NotNull(message = "La categoria del producto no puede ser nula")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Categoria categoria;

    @Column(name = "stock", nullable = false)
    @Min(value = 1, message = "El stock debe tener como valor mínimo 1.")
    private Integer stock;

    @Column(name = "precio", nullable = false)
    @Min(value = 0, message = "El precio debe tener como valor mínimo 0.")
    private Double precio;

    @Column(name = "porcentaje_impuesto", nullable = false)
    @Min(value = 0, message = "El procentaje de impuesto debe tener como valor mínimo 0.")
    @NotNull(message = "El porcentaje de impuesto no puede ser nulo")
    private Double porcentajeImpuesto;

    @Column(name = "habilitado", nullable = false)
    @NotNull(message = "El estado del producto no puede ser nulo")
    private Boolean habilitado;

    @OneToMany(mappedBy = "producto", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<MovimientoStock> movimientos;

    @OneToMany(mappedBy = "producto", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<LineaVenta> lineaVentas;

    @OneToMany(mappedBy = "producto", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<Imagen> imagenes;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, Categoria categoria, Integer stock, Double precio, Double porcentajeImpuesto, Boolean habilitado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.porcentajeImpuesto = porcentajeImpuesto;
        this.habilitado = habilitado;
    }

    public Producto(Long id) {
        super(id);
    }

    public String getNombre() {
        return nombre;
    }

    public List<MovimientoStock> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoStock> movimientos) {
        this.movimientos = movimientos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<LineaVenta> getLineaVentas() {
        return lineaVentas;
    }

    public void setLineaVentas(List<LineaVenta> lineaVentas) {
        this.lineaVentas = lineaVentas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPorcentajeImpuesto() {
        return porcentajeImpuesto;
    }

    public void setPorcentajeImpuesto(Double porcentajeImpuesto) {
        this.porcentajeImpuesto = porcentajeImpuesto;
    }

}
