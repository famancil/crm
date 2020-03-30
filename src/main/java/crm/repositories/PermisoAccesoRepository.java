package crm.repositories;

import crm.entities.PermisoAcceso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PermisoAcceso}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface PermisoAccesoRepository extends CrudRepository<PermisoAcceso, Short> {

    /**
     * Busca un {@link crm.entities.PermisoAcceso} segun su nombre (UNIQUE).
     *
     * @param nombre Nombre del {@link crm.entities.PermisoAcceso}
     * @return {@link crm.entities.PermisoAcceso} encontrado segun el nombre entregado por parametro.
     */
    public PermisoAcceso findByNombre(String nombre);

}
