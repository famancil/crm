package crm.repositories;

import crm.entities.TipoDisponibilidad;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoDisponibilidad}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoDisponibilidadRepository extends CrudRepository<TipoDisponibilidad, Short> {

    /**
     * Retorna una instancia de {@link crm.entities.TipoDisponibilidad} segun un id dado.
     * @param cod
     * @return Objeto de {@link crm.entities.TipoDisponibilidad}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    TipoDisponibilidad findByCodigo(Short cod);
}
