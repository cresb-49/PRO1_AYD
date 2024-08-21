package com.ayd1.APIecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayd1.APIecommerce.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Busqueda de usuario por email
    Usuario findByEmail(String email);
}
