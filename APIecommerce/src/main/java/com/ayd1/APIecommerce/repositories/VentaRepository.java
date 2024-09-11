/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ayd1.APIecommerce.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ayd1.APIecommerce.models.Venta;

/**
 *
 * @author Luis Monterroso
 */
public interface VentaRepository extends CrudRepository<Venta, Long> {

    @Query(value = "SELECT * FROM venta e "
            + "WHERE (:fechaInicio IS NULL OR DATE(e.created_at) >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(e.created_at) <= :fechaFin)",
            nativeQuery = true)
    public List<Venta> findAllByCreatedAtDateBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);

    public List<Venta> findAllByDatosFacturacion_Usuario_Id(Long id);
}
