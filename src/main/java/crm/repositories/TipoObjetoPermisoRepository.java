package crm.repositories;

import crm.entities.TipoNivelFacturacion;
import crm.entities.TipoObjetoPermiso;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoObjetoPermiso}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface TipoObjetoPermisoRepository extends CrudRepository<TipoObjetoPermiso, Short>{

    /**
     * Obtiene una lista de {@link crm.entities.TipoObjetoPermiso}
     * @return Collection ({@link java.util.List}) de {@link crm.entities.TipoObjetoPermiso}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    List<TipoObjetoPermiso> findAll();
}
