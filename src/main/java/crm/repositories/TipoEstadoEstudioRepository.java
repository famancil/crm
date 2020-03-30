package crm.repositories;

import crm.entities.TipoEstadoEstudio;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoEstudio}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoEstadoEstudioRepository extends CrudRepository <TipoEstadoEstudio, Short> {
}
