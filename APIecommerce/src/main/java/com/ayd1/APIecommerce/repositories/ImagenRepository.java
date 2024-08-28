/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.repositories;

import com.ayd1.APIecommerce.models.Imagen;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface ImagenRepository extends CrudRepository<Imagen, Long> {

    @Override
    public Optional<Imagen> findById(Long id);
}
