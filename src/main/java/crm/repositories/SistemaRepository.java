package crm.repositories;

import crm.entities.Sistema;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Sistema}.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
public interface SistemaRepository extends CrudRepository<Sistema, Short>{
}
