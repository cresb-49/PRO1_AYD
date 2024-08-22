
package com.ayd1.APIecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayd1.APIecommerce.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
}

