package com.ayd1.APIecommerce.repositories;

import org.springframework.stereotype.Repository;

import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.dto.reports.ClienteFrecuenteDto;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    //Busqueda de usuario por email
    public Optional<Usuario> findByEmail(String email);

    public Optional<Usuario> findByCodigoRecuperacion(String codigo);

    public Long deleteUsuarioById(Long id);

    public boolean existsByEmail(String email);

    public boolean existsByNit(String nit);

    public boolean existsUsuarioByEmailAndIdNot(String email, Long id);

    public boolean existsUsuarioByNitAndIdNot(String nit, Long id);

    @Override
    public List<Usuario> findAll();

    @Query("SELECT new com.ayd1.APIecommerce.models.dto.reports.ClienteFrecuenteDto("
            + "df.usuario.id, "
            + "df.usuario.nombres, "
            + "df.usuario.apellidos, COUNT(v.id), SUM(v.valorTotal),"
            + " SUM(v.valorTotal) / COUNT(v.id)) "
            + "FROM DatosFacturacion df "
            + "JOIN df.venta v "
            + "WHERE (:fechaInicio IS NULL OR v.createdAt >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR v.createdAt <= :fechaFin) "
            + "GROUP BY df.usuario.id "
            + "ORDER BY SUM(v.valorTotal) DESC")
    List<ClienteFrecuenteDto> obtenerReporteClientesFrecuentes(
            @Param("fechaInicio") Instant fechaInicio,
            @Param("fechaFin") Instant fechaFin);

}
