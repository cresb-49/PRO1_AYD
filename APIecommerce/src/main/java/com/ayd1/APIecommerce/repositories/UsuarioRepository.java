package com.ayd1.APIecommerce.repositories;

import org.springframework.stereotype.Repository;

import com.ayd1.APIecommerce.models.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    //Busqueda de usuario por email
    public Optional<Usuario> findByEmail(String email);

    public Optional<Usuario> findByCodigoRecuperacion(String codigo);

    public Long deleteUsuarioById(Long id);

    boolean existsByEmail(String email);

    boolean existsByNit(String nit);

    boolean existsUsuarioByEmailAndIdNot(String email, Long id);

    boolean existsUsuarioByNitAndIdNot(String nit, Long id);

    @Override
    public List<Usuario> findAll();
}
