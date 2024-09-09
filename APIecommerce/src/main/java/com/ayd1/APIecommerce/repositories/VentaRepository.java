/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ayd1.APIecommerce.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ayd1.APIecommerce.models.Venta;

/**
 *
 * @author Luis Monterroso
 */
public interface VentaRepository extends CrudRepository<Venta, Long> {

    public List<Venta> findAllByCreatedAtBetween(Instant fecha1, Instant fecha2);
    public List<Venta> findAllByDatosFacturacion_Usuario_Id(Long id);
}
