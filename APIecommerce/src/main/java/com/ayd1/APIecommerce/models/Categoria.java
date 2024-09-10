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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "categoria")
@DynamicUpdate
@SQLDelete(sql = "UPDATE categoria SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Categoria extends Auditor {

    @ManyToOne
    @JoinColumn(name = "id_padre")
    private Categoria padre;

    @Column(name = "nombre", length = 250, unique = true)
    @NotBlank(message = "El nombre de la categoria no puede estar vacío.")
    @NotNull(message = "El nombre de la categoria no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre de la categoria debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @OneToMany(mappedBy = "categoria", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<Producto> productos;

    public Categoria(Categoria padre, String nombre) {
        this.padre = padre;
        this.nombre = nombre;
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Categoria() {
    }

    public Categoria(Long id) {
        super(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Categoria getPadre() {
        return padre;
    }

    public void setPadre(Categoria padre) {
        this.padre = padre;
    }

}
