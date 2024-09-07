/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ayd1.APIecommerce.repositories;

import com.ayd1.APIecommerce.models.Venta;
import java.time.Instant;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Luis Monterroso
 */
public interface VentaRepository extends CrudRepository<Venta, Long> {

    public List<Venta> findAllByCreatedAtBetween(Instant fecha1, Instant fecha2);
}
