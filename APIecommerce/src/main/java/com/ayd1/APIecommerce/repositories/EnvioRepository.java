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
import org.springframework.stereotype.Repository;

import com.ayd1.APIecommerce.models.Envio;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface EnvioRepository extends CrudRepository<Envio, Long> {

    @Override
    public List<Envio> findAll();

    public List<Envio> findAllByEstadoEnvio_Nombre(String nombre);

    @Query(value = "SELECT * FROM envio e "
            + "WHERE (:fechaInicio IS NULL OR DATE(e.created_at) >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(e.created_at) <= :fechaFin)", nativeQuery = true)
    public List<Envio> findAllByCreatedAtDateBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}
