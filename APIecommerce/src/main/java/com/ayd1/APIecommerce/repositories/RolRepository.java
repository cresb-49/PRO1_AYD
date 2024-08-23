package com.ayd1.APIecommerce.repositories;

import org.springframework.stereotype.Repository;

import com.ayd1.APIecommerce.models.Rol;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {

    public Optional<Rol> findOneByNombre(String nombre);

}
