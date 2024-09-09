package com.ayd1.APIecommerce.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ayd1.APIecommerce.models.Permiso;

@Repository
public interface PermisoRepository extends CrudRepository<Permiso, Long> {

    public Optional<Permiso> findOneByNombre(String nombre);

    public boolean existsByNombre(String nombre);
}
