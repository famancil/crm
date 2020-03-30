package crm.repositories;

import crm.entities.TipoActividadExalumno;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoActividadExalumno}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoActividadExalumnoRepository extends CrudRepository<TipoActividadExalumno, Short>{
}
