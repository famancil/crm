package crm.repositories;

import crm.entities.AccesoSistema;
import crm.entities.AccesoSistemaPK;
import crm.entities.RolAcceso;
import crm.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RolAcceso}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface RolAccesoRepository extends CrudRepository<RolAcceso, Short>{

    List<RolAcceso> findAll();

    RolAcceso findByNombre(String nombreRolAcceso);

    List<RolAcceso> findAllByOrderByIdAsc();
}
